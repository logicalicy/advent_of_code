export const assert = (conditional: boolean, msg: string) => {
  if (!conditional) {
    throw new Error(msg);
  }
};
