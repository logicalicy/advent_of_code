import { assert } from "./lib";

export class FileSystem {
  fileSystemRoot: FileSystemDir;
  currentPath: string;
  prevPath: string | null;
  commandOutputs: [Command, Arg, LsOutput[]][];
  constructor(commandOutputs: [Command, Arg, LsOutput[]][]) {
    this.currentPath = "/";
    this.prevPath = null;
    this.fileSystemRoot = {
      _path: "/",
      _diskUsage: 0,
    };
    this.commandOutputs = commandOutputs;
  }
  static getPathComponents(path: string) {
    return path.split("/").filter(Boolean);
  }
  static getPrevPath(path: string) {
    if (path === "/") {
      return null;
    }
    const pathComponents = FileSystem.getPathComponents(path);
    const initial = pathComponents.slice(0, pathComponents.length - 1);
    return `/${initial.join("/")}/`;
  }
  changeDir(path: string) {
    const pathTokens = FileSystem.getPathComponents(path);
    let currentPath: string = null;
    let currentDir = this.fileSystemRoot as FileSystemDir;
    pathTokens.forEach((token) => {
      currentPath = `${currentPath ? currentPath : "/"}${token}/`;
      if (!currentDir[token]) {
        currentDir[token] = {
          _path: currentPath,
          _diskUsage: 0,
        };
      }
      if (typeof currentDir[token] !== "number") {
        currentDir = currentDir[token] as FileSystemDir;
      }
    });
    return currentDir;
  }
  writeToPath(path: string, lsOutput: LsOutput[]) {
    const dir = this.changeDir(path);
    lsOutput.forEach(([data, nodeName]) => {
      if (data !== "dir") {
        // File.
        dir[nodeName] = data;
      }
    });
  }
  calcDiskUsage(dir: FileSystemDir) {
    let diskUsage = 0;
    Object.entries(dir).forEach(([nodeName, nodeValue]) => {
      if (nodeName === "_path") {
        // Skip.
        return;
      }
      if (typeof nodeValue === "number") {
        diskUsage += nodeValue;
        return;
      }
      const node = dir[nodeName] as FileSystemDir;
      diskUsage += this.calcDiskUsage(node);
    });
    dir._diskUsage = diskUsage;
    return diskUsage;
  }
  getDirs(dir: FileSystemDir) {
    let dirs: [string, number][] = [
      [dir._path as string, dir._diskUsage as number],
    ];
    Object.entries(dir).forEach(([nodeName, nodeValue]) => {
      if (nodeName === "_path" || nodeName === "_diskUsage") {
        // Skip.
        return;
      }
      if (typeof nodeValue === "number") {
        return;
      }
      const node = dir[nodeName] as FileSystemDir;
      dirs = [...dirs, ...this.getDirs(node)];
    });
    return dirs;
  }
  create() {
    const commandOutputs = this.commandOutputs;
    console.log(JSON.stringify(this.commandOutputs, null, 2));
    let currentPath = "/";
    let prevPath: string | null = null;
    for (let i = 0; i < commandOutputs.length; i += 1) {
      const commandOutput = commandOutputs[i];
      const command = commandOutput[0];
      const arg = commandOutput[1];
      const lsOutput = commandOutput[2];
      assert(Array.isArray(lsOutput), "`ls` output must be an array.");
      assert(["ls", "cd"].includes(command), "Unsupported command.");
      assert(
        typeof currentPath === "string",
        `Current path must be string: ${currentPath}`
      );
      if (command === "ls") {
        this.writeToPath(currentPath, lsOutput);
      } else if (command === "cd") {
        if (arg === "..") {
          // Step out of dir.
          currentPath = FileSystem.getPrevPath(currentPath);
          prevPath = FileSystem.getPrevPath(prevPath);
        } else {
          // Step into dir.
          prevPath = currentPath;
          currentPath = `${currentPath}${arg}/`;
        }
      }
    }
    this.calcDiskUsage(this.fileSystemRoot);
    return this.fileSystemRoot;
  }
}
