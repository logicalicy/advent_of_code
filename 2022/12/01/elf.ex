defmodule Elf  do
  def sum_list([head | tail], acc) do
    Elf.sum_list(tail, head + acc)
  end
  def sum_list([], acc) do
    acc
  end

  def max_in_list([head | tail], acc) do
    Elf.max_in_list(tail, max(head, acc))
  end
  def max_in_list([item], acc) do
    max(item, acc)
  end
  def max_in_list([], acc) do
    acc
  end

  def max_three(vals) do
    sorted = Enum.sort(vals)
    [a | [b | [c | tail]]] = Enum.reverse(sorted)
    [a, b, c]
  end

  def parse_int_list([head | tail]) do
    {val, _} = Integer.parse(head)
    [val] ++ Elf.parse_int_list(tail)
  end
  def parse_int_list([]) do
    []
  end

  def sum_calories ([cals_by_elf | tail]) do
    elf_cals = String.split(cals_by_elf, "\n", [trim: true])
    parsed_list = Elf.parse_int_list(elf_cals)
    total_calories = Elf.sum_list(parsed_list, 0)
    IO.puts("Elf: " <> Integer.to_string(total_calories))
    [total_calories] ++ Elf.sum_calories(tail)
  end
  def sum_calories([]) do
    []
  end

  def get_input() do
    path = Path.dirname(__ENV__.file) <> "/input.txt"

    IO.puts("Current file path: " <> path)

    {:ok, contents} = File.read(path)

    String.trim(contents)
  end
end
