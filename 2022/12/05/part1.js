const fs = require('fs');

const getInput = () => {
  const file = fs.readFileSync('./input.txt');
  return file.toString();
};
module.exports.getInput = getInput;

const filterInitialState = (lines) => {
  const initialState = [];
  for (let line of lines) {
    if (line.trim() === '') {
      // First empty line.
      // Everything up to this point is initial state.
      return initialState;
    } else {
      initialState.push(line);
    }
  }
  return initialState;
};

const filterInstructions = (lines) => {
  const instructions = [];
  let isInstruction = false;
  for (let line of lines) {
    if (isInstruction && line.trim() !== '') {
      instructions.push(line);
    }
    if (line.trim() === '') {
      // First empty line.
      // Everything after this are the instructions.
      isInstruction = true;
    }
  }
  return instructions;
};

const parseNumStacks = (firstLine) => {
  const nums = firstLine.trim().split(/\s+/);
  return nums.length;
};

const chunk = (str, chunkSize) => {
  let startIdx = 0;
  let endIdx = chunkSize;
  let result = [];
  while (startIdx < str.length) {
    result = [...result, str.slice(startIdx, endIdx)];
    startIdx += chunkSize;
    endIdx += chunkSize;
  }
  return result;
};
module.exports.chunk = chunk;

const removeLeadingNil = (arr) => {
  return arr.reduce((acc, v) => {
    if (v === null && acc.length === 0) {
      return acc;
    }
    return [...acc, v];
  }, []);
};

const parseInitialState = (lines) => {
  const [firstLine, ...rest] = [...lines].reverse();
  const numStacks = parseNumStacks(firstLine);
  const stacks = [];
  for (let i = 0; i < numStacks; i += 1) {
    stacks.push([]);
  }
  for (const line of rest) {
    const chunks = chunk(line.trim(), 4).map(c => c.trim());
    chunks.forEach((c, cIdx) => {
      if (c) {
        const matches = c.match(/\[([^\]]+)\]/);
        const match = matches && matches[1]
        stacks[cIdx].push(match);
      } else {
        stacks[cIdx].push(null);
      }
    });
  }
  return stacks.map((stack) => {
    const reversed = stack.reverse();
    const leadingNilsRemoved = removeLeadingNil(reversed);
    return leadingNilsRemoved.reverse();
  });
};
module.exports.parseInitialState = parseInitialState;

const parseInstruction = (line) => {
  const matches = line.match(/move (\d+) from (\d+) to (\d+)/);
  if (matches) {
    return [
      parseInt(matches[1], 10),
      parseInt(matches[2], 10),
      parseInt(matches[3], 10),
    ];
  }
  throw new Error('Could not parse instruction');
};

const parseInstructions = (lines) => {
  const fromToPairs = lines.reduce((acc, line) => {
    const fromToPair = parseInstruction(line);
    return [...acc, fromToPair];
  }, []);
  return fromToPairs;
};
module.exports.parseInstructions = parseInstructions;

const parseInput = (input) => {
  const lines = input.split('\n');

  const initialStateLines = filterInitialState(lines);
  const instructionLines = filterInstructions(lines);

  const initialState = parseInitialState(initialStateLines);
  const instructions = parseInstructions(instructionLines);

  return [initialState, instructions];
};
module.exports.parseInput = parseInput;

const executeInstructions = (state, instructions) => {
  for (const instruction of instructions) {
    const [count, fromStackNum, toStackNum] = instruction;
    const fromStackIdx = fromStackNum - 1;
    const toStackIdx = toStackNum - 1;
    const fromStack = state[fromStackIdx];
    const toStack = state[toStackIdx];

    for (let i = 0; i < count; i += 1) {
      const val = fromStack.pop();
      toStack.push(val);
    }
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
