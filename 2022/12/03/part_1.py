import os

dirname = os.path.dirname(__file__)
DIR_PATH = os.path.join(dirname, './input.txt')

def get_priorities_by_char():
    priorities_by_char = {}
    chars = []
    for num in range(97, 123):
        # "a" to "z".
        chars.append(chr(num))
    for num in range(65, 91):
        # "A" to "Z".
        chars.append(chr(num))
    for (idx, char) in enumerate(chars):
        priorities_by_char[char] = idx + 1 # 1-indexed.
    return priorities_by_char


PRIORITIES_BY_CHAR = get_priorities_by_char()


def to_priority(ch):
    return PRIORITIES_BY_CHAR[ch]


def to_compartments(str):
    stripped = str.strip()
    assert (len(stripped) % 2) == 0, "Must be even number of characters"
    half_len = int(len(stripped) / 2)
    return (stripped[:half_len], stripped[-half_len:])


def get_common_items(str_a, str_b):
    common_items = set(str_a) & set(str_b)
    return list(common_items)


def main():
    with open(DIR_PATH, "r") as f:
        common_item_priorities = []
        for line in f.readlines():
            (compartment_a, compartment_b) = to_compartments(line)
            common_items = get_common_items(compartment_a, compartment_b)
            assert len(common_items) <= 1, "Must be up to one common item type"
            if len(common_items) == 1:
                common_item_priorities.append(
                  to_priority(common_items[0])
                )
        print(sum(common_item_priorities))


main()
