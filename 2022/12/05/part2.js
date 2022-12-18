const { getInput, parseInput } = require('./part1');

const executeInstructions = (state, instructions) => {
  for (const instruction of instructions) {
    const [count, fromStackNum, toStackNum] = instruction;
    const fromStackIdx = fromStackNum - 1;
    const toStackIdx = toStackNum - 1;
    const fromStack = state[fromStackIdx];
    const toStack = state[toStackIdx];

    state[toStackIdx] = [...toStack, ...fromStack.slice(fromStack.length - count, fromStack.length)];
    state[fromStackIdx] = fromStack.slice(0, fromStack.length - count);
  }
  return state;
};

const run = () => {
  const input = getInput();
  const [initialState, instructions] = parseInput(input);
  const endState = executeInstructions(initialState, instructions);
  const lastCrates = endState.map(stack => stack[stack.length - 1]);
  console.log(lastCrates.join(''));
};
module.exports.run = run;
