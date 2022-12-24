import { assert } from "./lib";

export class Parser {
  lines: string[][];
  constructor(lines: string[][]) {
    this.lines = lines;
  }
  parseCommand(cmd: string): Command {
    assert(["ls", "cd"].includes(cmd), "Must be one of `ls` and `cd`.");
    return cmd as Command;
  }

  parseArg(arg: string | undefined) {
    assert(
      typeof arg === "string" || typeof arg === "undefined",
      "Must be type string or undefined."
    );
    return arg;
  }

  parseNodeData(nodeData: string): NodeData {
    if (nodeData === "dir") {
      return nodeData;
    }
    const fileSize = parseInt(nodeData, 10);
    assert(Number.isFinite(fileSize), `File size must be number: ${nodeData}`);
    return fileSize;
  }

  parseLine(tokens: string[]): CommandArg | LsOutput {
    const firstToken = tokens[0];
    if (firstToken === "$") {
      // Parsing a command.
      const command = this.parseCommand(tokens[1]);
      if (command === "cd") {
        // `cd` command.
        const arg = this.parseArg(tokens[2]); // E.g. if command is `cd`.
        return [command, arg];
      }
      // `ls` command. Doesn't have arg.
      return [command, undefined];
    }
    // `ls` output.
    return [this.parseNodeData(tokens[0]), tokens[1]];
  }

  parseCommandOutputs(
    parsedLines: (CommandArg | LsOutput)[]
  ): [Command, Arg, LsOutput[]][] {
    let result: [Command, Arg, LsOutput[]][] = [];
    for (let i = 0; i < parsedLines.length; i += 1) {
      const parsedLine = parsedLines[i];
      const firstToken = parsedLine[0];
      const secondToken = parsedLine[1];
      if (firstToken === "cd") {
        result.push([firstToken, secondToken, []]);
      } else if (firstToken === "ls") {
        result = [...result, [firstToken, undefined, []]];
      } else {
        // Must be last `ls` output line.
        const last = result[result.length - 1];
        last[2].push([firstToken, secondToken]);
      }
    }
    return result;
  }

  parse() {
    const parsedLines = this.lines
      .filter((line) => line.filter(Boolean).length > 0)
      .map((line) => this.parseLine(line));
    const parsedCommandOutputs = this.parseCommandOutputs(parsedLines);
    return parsedCommandOutputs;
  }
}
