import { Lexer } from "./lexer";
import { Parser } from "./parser";

const INPUT = `
$ cd /
$ ls
dir bzcg
dir hrtvrp
dir jvj
dir ltrqb
dir msqlnht
dir mvs
dir nzmddp
dir zjvncc
$ cd bzcg
$ ls
dir fwmbbvj
286838 hclnfzgv.gqb
dir mpsthvvc
76013 qgzdlv.vdh
186898 znzszz
dir zwmp
`.trim();

describe("parser", () => {
  describe("Parser", () => {
    const lexer = new Lexer(INPUT);
    const parser = new Parser(lexer.tokenize());
    describe("parse", () => {
      it("parses input", () => {
        const actual = parser.parse();
        expect(actual).toEqual([
          ["cd", "/", []],
          [
            "ls",
            undefined,
            [
              ["dir", "bzcg"],
              ["dir", "hrtvrp"],
              ["dir", "jvj"],
              ["dir", "ltrqb"],
              ["dir", "msqlnht"],
              ["dir", "mvs"],
              ["dir", "nzmddp"],
              ["dir", "zjvncc"],
            ],
          ],
          ["cd", "bzcg", []],
          [
            "ls",
            undefined,
            [
              ["dir", "fwmbbvj"],
              [286838, "hclnfzgv.gqb"],
              ["dir", "mpsthvvc"],
              [76013, "qgzdlv.vdh"],
              [186898, "znzszz"],
              ["dir", "zwmp"],
            ],
          ],
        ]);
      });
    });
  });
});
