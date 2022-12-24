type LsCommand = "ls";
type Command = LsCommand | "cd";
type Arg = string | undefined;
type CommandArg = [Command, Arg];
type NodeMetadata = string;
type NodeData = "dir" | number;
type NodeName = string;
type LsOutput = [NodeData, NodeName];
type FileSystemDir = {
  _path: string;
  _diskUsage: number;
  [key: NodeName]: FileSystemDir | NodeData | NodeMetadata;
};
