import { FileSystem } from "./file_system";
import * as fs from "fs";
import { Lexer } from "./lexer";
import { Parser } from "./parser";

const MAX_DISK = 70000000;
const MIN_DISK_FOR_UPDATE = 30000000;

const main = async () => {
  // Part #1.
  const content = fs.readFileSync("./input.txt").toString();
  const lexer = new Lexer(content);
  const lines = lexer.tokenize();
  const parser = new Parser(lines);
  const commandOutputs = parser.parse();
  const fileSystem = new FileSystem(commandOutputs);
  const fileSystemRoot = fileSystem.create();
  const dirs = fileSystem.getDirs(fileSystemRoot);
  const DISK_USAGE_LIMIT = 100000;
  const dirsBelowDiskUsageLimit = dirs.filter(
    ([path, diskUsage]) => diskUsage <= DISK_USAGE_LIMIT
  );
  const totalDiskUsageWithLimit = dirsBelowDiskUsageLimit.reduce(
    (acc, [path, diskUsage]) => acc + diskUsage,
    0
  );
  console.log("Total size of dirs <= 100K:", totalDiskUsageWithLimit);

  // Part #2.
  const totalFreeDisk = MAX_DISK - fileSystemRoot._diskUsage;
  const diskNeededForUpdate = MIN_DISK_FOR_UPDATE - totalFreeDisk;
  const potentialDirsToDelete = dirs.filter(
    ([path, diskUsage]) => diskUsage >= diskNeededForUpdate
  );
  const sortedDirs = potentialDirsToDelete.sort((a, b) => {
    return a[1] - b[1];
  });
  console.log(sortedDirs.slice(0, 5));
  const dirToDelete = sortedDirs[0];
  console.log("Max disk:", MAX_DISK);
  console.log("Free disk:", totalFreeDisk);
  console.log("Min disk for update:", MIN_DISK_FOR_UPDATE);
  console.log("Disk needed for update:", diskNeededForUpdate);
  console.log("Dir to delete:", dirToDelete[0]);
  console.log("Dir size:", dirToDelete[1]);
};

main();
