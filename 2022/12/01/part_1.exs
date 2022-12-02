contents = Elf.get_input()

elves = String.split(contents, "\n\n", [trim: true])

cals = Elf.sum_calories(elves)

max_cals = Elf.max_in_list(cals, 0)

IO.puts("Calories:\n" <> Integer.to_string(max_cals))
