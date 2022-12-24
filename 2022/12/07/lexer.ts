export class Lexer {
  content: string;
  constructor(content: string) {
    this.content = content;
  }
  tokenize = () => {
    return this.content.split("\n").map((line) => line.split(" "));
  };
}
