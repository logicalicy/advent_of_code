import { FileSystem } from "./file_system";

describe("file_system", () => {
  describe("FileSystem", () => {
    describe("getPathComponents", () => {
      it("returns [] if path is '/'", () => {
        const actual = FileSystem.getPathComponents("/");
        expect(actual).toEqual([]);
      });
      it("returns ['foo', 'bar'] if path is '/foo/bar/'", () => {
        const actual = FileSystem.getPathComponents("/foo/bar/");
        expect(actual).toEqual(["foo", "bar"]);
      });
    });
    describe("getPrevPath", () => {
      it("returns null if path is '/'", () => {
        const actual = FileSystem.getPrevPath("/");
        expect(actual).toEqual(null);
      });
      it("returns /foo/ if path is '/foo/bar/'", () => {
        const actual = FileSystem.getPrevPath("/foo/bar/");
        expect(actual).toEqual("/foo/");
      });
      it("returns /foo/bar/ if path is '/foo/bar/baz/'", () => {
        const actual = FileSystem.getPrevPath("/foo/bar/baz/");
        expect(actual).toEqual("/foo/bar/");
      });
    });
  });
});
