const { chunk, parseInitialState } = require('./part1');

describe('part1', () => {
  describe('chunk', () => {
    it('chunks a string', () => {
      const actual = chunk('abcdefghijk', 4);
      expect(actual).toEqual([
        'abcd',
        'efgh',
        'ijk',
      ]);
    })
  });
  describe('parseInitialState', () => {
    it('parses initial state', () => {
      const initialState = parseInitialState([
        '[S]        ',
        '[L]        ',
        '[F]     [S]',
        '[Z] [R] [N]',
        '[D] [Z] [H]',
        '[B] [M] [C]',
        '[R] [B] [L]',
        '[H] [T] [Z]',
        ' 1   2   3 ',
      ]);
      expect(initialState).toEqual([
        ['H', 'R', 'B', 'D', 'Z', 'F', 'L', 'S'],
        ['T', 'B', 'M', 'Z', 'R'],
        ['Z', 'L', 'C', 'H', 'N', 'S'],
      ]);
    })
  });
});
