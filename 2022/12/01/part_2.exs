contents = Elf.get_input()

elves = String.split(contents, "\n\n", [trim: true])

cals = Elf.sum_calories(elves)

max_cals = Elf.max_three(cals)

total_cals = Elf.sum_list(max_cals, 0)

IO.puts("Calories:\n" <> Integer.to_string(total_cals))
