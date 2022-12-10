import os
import functools
from part_1 import to_priority


dirname = os.path.dirname(__file__)
DIR_PATH = os.path.join(dirname, './input.txt')


def chunked(arr, chunk_size = 1):
    return [arr[i:i+chunk_size] for i in range(0, len(arr), chunk_size)]


def get_common_items(strings):
    common_items = functools.reduce(
      lambda acc, x: acc & x,
      [set(str) for str in strings]
    )
    return list(common_items)


def main():
    with open(DIR_PATH, "r") as f:
        common_item_priorities = []
        lines = [line.strip() for line in f.readlines()]
        chunks = chunked(lines, 3)
        for chunk in chunks:
            common_items = get_common_items(chunk)
            assert len(common_items) <= 1, "Must be up to one common item type"
            if len(common_items) == 1:
                common_item_priorities.append(
                  to_priority(common_items[0])
                )
        print(sum(common_item_priorities))


main()
