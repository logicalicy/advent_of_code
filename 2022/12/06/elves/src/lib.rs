use std::fs;
use std::path::PathBuf;

mod utils;

use wasm_bindgen::prelude::*;
use std::{collections::HashSet, iter::FromIterator};

// When the `wee_alloc` feature is enabled, use `wee_alloc` as the global
// allocator.
#[cfg(feature = "wee_alloc")]
#[global_allocator]
static ALLOC: wee_alloc::WeeAlloc = wee_alloc::WeeAlloc::INIT;

#[wasm_bindgen]
extern {
    fn alert(s: &str);
}

fn is_unique(s: &str) -> bool {
  let set: HashSet<char> = HashSet::from_iter(s.chars().into_iter());
  set.len() == s.len()
}

fn find_first_distinct_substring_index(text: &str, substring_length: usize) -> usize {
  let mut idx: usize = 0;
  let mut chars = &text[idx..idx+substring_length];
  while idx < text.len() {
    chars = &text[idx..idx+substring_length];
    if is_unique(chars) {
      break;
    }
    idx = idx + 1;
  }
  assert_ne!(idx, 0);
  idx + substring_length
}

fn find_start_of_packet_index(text: &str) -> usize {
  return find_first_distinct_substring_index(text, 4);
}

fn find_start_of_message_index(text: &str) -> usize {
  return find_first_distinct_substring_index(text, 14);
}

#[wasm_bindgen]
pub fn main() {
  alert("Loading input.txt...");
  let contents = "pfptpztzfznzzznszzfgzgqgbgzgmzggwlwnlngnddjttzwtwmttlrrlqqzczpzhppmjmnjnfjnjjjprpfpnfpnpznzbbnwbbdsbswwwwszzzcmcttbftffczctztffppcvccbdbhbtbcbhbrbnrbrzbztbblvlvqvcqvcvcrcprrmffhfwfjwjrjtttvqqsttcwtthwthhqssbbqhhcbhbhqqwsqspqspprbrsrmmvhvvlsvspvpddbvdbdhhgshggfbgbrggzszsnshsvhsvhhnfnhfnhhbzzthtstdssdsrdsrdrtddsrdrcrgcgdcggdbbtgbgzbzwwnlngggspggspspjjfqflqqttjbbnffszfzjjlwjwhjwjccpllzztrzttdpdbbcnntwnnctnnrrgbrbvbccvzvlvnllvrvnnvmmtrmrddjdqdrqrmmvssgllsjsrrwggvcccgwwgddzldzldzdttqrrtggdngdgfdgfgzztdtthrttszzgjglldzdhdphppjfjfzfpfsflfggpwwwjdjssqdqmdmdpmmvbvfvpfpfwfpwfppbgbwgbbpmpwpzplpjpttnwnlnppcvchhmwwmllwppwspwspswpphhdhfhccwpcplpzzjwjssfrsssgnglnglnglngncgcvvpddrcrjrssznnjllslrlmllglpggwhhllzqzssvtsvvbpphpthphdhphnhdhdqdbbmcmqcmcmddtwtqqsbbzddtzdzrzmmfccdttlvljlffzczmzrrtzzrbrllfnfmfnnrcnnctcnnncbcrbccwcnwcnnrbnrrpnprrtwwvcwchwwcbwwwnpnvvgtvvqmvvhttrnnvjjtwtfwtwrrbpbmbmzmpphwphwwqwgwlwclwccjvccsllhhrtrstthvvfjjcvcdcbdcbczcttjbttwtvvvzjzgzwgwjjsnnvrrlbrrvnvdvhvchvvglvvpssdbsszddsqsspvpbpjjgghvvlwvlvggpjjmcjmmmhjmmrgrsgrsggchhcmmqsmsttlslqqmjjbpbrrcnrnbbvqvsvtvftvtmmqzzsnscsffnrnmrmnnszsllsrrrzrszsbbchccsrsmshhzrhrrdlrrjfmlrfhvqqvmpbrntgcqqsqvjmtctflbffddfbsjvzsfdwblprszhfvltwtcfsbdlwjgsmlcrvgstjqtrtnqzbmrmgqnscqjdfnbppcdgcsstwdmdvphsqmrfmzwntjgjjvcdgbhfjqlzglgjdsdlhwwrmfqcfsvhwwfmvprpnmjppvwzjwmddtndspzqjqrpbpnrjfwqfvbtqrgngcbjvhnfbtslcpppbsfhbcmwgpccftwhnbvdrzqdtwnrtjcdlnlmhvlzvljwrzgtfjrpgzjvggtpsvcdgtsvhdzvtfbwmnptfmllgcvfmmgvpbrgnhcnpwltqmjmltsbpzmfrttsmjqwhncvtqrsmcpsnrqzmwftbltllbhzhdfzmfgbvdtgwpvngsffjmwhfhmccfrjgqcqngzlnqvsgrcdzbsmjbmflwvhjldlrdvjrmgvjvpcczdhczpbtwphvhqmhcnljbwnzqwmbctffmctlcmhzcnvprdhtzdvgbhlnnjqzcwcsrgzjjlszssnplwqjlczvftmnbnmdpbjnctnslhgsjswqjwvprdstvbstlnnwwgvsffwmprjrlfccmtgvqbghvhcngwwtzwbwcdmrfstwhtfghgvzbfgtwjglcllwrhgdzptvrrbdhbscjhmtswshjmrsbpzstwmhrwwwncbbmjnjjlzrpdrzfvstbltszvlhcqbcpgbwtzzslsrljmhmtlcvzdbszvnjhrswrjrmfpsfpplwlrsnrpnjngmhwpwqcmtslhbmlsmjhcgmzznftmhvtmzlvmcwnbqtcntqghrqcsztsgzrnmrlvrnhtpmstdflpztmwltvgppttfwhhzzgrffjchswhbljvcjwvvnqnvdvjpsclhwsrtczvjmtcsnwvnwtdllphmrthddfvcjwvggqltmhglllmqzjsbjwgdqwzzmjnrmpbqplgzjgzcdqmtsntprdwwjwthcbsghqqspszndgqdmlzdlzwfcghtbhcqpbpmnfgqzmhtnttvjttvzhllsjvmmcmmppcgssjhnzqwpzdbtmrzsfbvgmgbtbwjrzvdlmgjpzltfmcclpltsszpqbllrwbwsnbhhvfwphrcpdvbjhgmgpphrdpcmjvfsjzrqldlqthwsztzcgttdnzcsbnszcsvmcspddlmwjttggdmlpqrdrfmwfzpdbnrwtmwssvbwtmzhndmhzwtlgdwpbrzghmlbszswqlpzldbvswjgtvjvmtjwdggfsbggbwhpwjdmflhmsgtbzrtbvlpqqmpcrbhflnfmwwsvdsgnnznfrqhqgqfgdfzcdqrtdftsntpbcclhncqjjwvszmssswnscwjlpfvdvltgcmqqttnfvbptbbmlrvrwwfbwwbvlrdrfmscqwdvdjgdrghwfjsttvwngzttzzsmzqnvzdfsvrbrcwtmmjdvnzjzdsnzgtszzcwdphnjmspmdsrqwgdwlzrgghcchpbltmwnjrbhqhzdqqmbrpggjjwnqfnnsqsfzbwqjsfprvrvfwbqvhgpjvqzplnhtqszqrtsvtbptfvzmvjhshbtmqbmqrrwplzphvdvhttlmftdwltqssstzlvnslzhnmjdlsbdprbgpjvcdtcfchzqqqcnngbrmntjwfbvcdcfgbcpnvcbbcvhqfzpsmgbcvrqvjlqlqnvvzdgphfpgtrpbbwztvqjgdpnpwbffdgqzmqvgblwzmdrhwdprhcqppcggrldhcdztnhspclfcwttnqslnzvvshcwgfztvscvztdrprvnlmfsgcpfdmfjgblnhrbsmjrjdzjwvwmlllvscsvfqvhdsdljrqphcvvtcttbwvnwwzwshdcfdqnjszltmddzjgmqgvpjzpzssrmfsrgjhvqtlhsfnndnqhpbcnltmdvlhfwqcmwnqbhsfqqwnfnnfjjbsqmcdrrvlfztdprnmjfhlvcdbjtczbrpljmpcwvchdwrqbwggjnrlhcdgzfwjzzjgfnbpwbpvswqdpcrthwfcffgztrjqntczfcbsrrtrjrwgbbgjshtzvjjlqqtsbgmpsttqjqwgmmbzhshqvvrcgbdsqmtqlrgjbnvbrpzdrgqnzstfdcvdnjhcnjblmsqtfvstlptgrczhbgpllpqwfdmthgjlltmlnltzpvjvjfgtrzslsptlfplgrgpjsbhbbbwlljfdjnhqcndlprfbwpvddndpnqwqccgbqmwlrffpzjpqclwcrgjgljwzpppwltcwdqdchghfnwbhrjndjsvlqnnmlrjfgfpnvgmlhbgnhnztpjzdmltfmjtzclsbspvhfngtjmzwrwmprdfplzzwfrdnbmbgvjlczcdvmpfmtqmzjrpfhjwwzmtnzmptwnhtlbndcpshqrqqrpccqpnvnqqdprvccmdmrsbptdhrhlpcptgfsfwphfpvbcrlnbrtgwcpgjclhhvpjhcwcgghlzbmpbswgtzqhmlwdfrrdfvnbhlqhvhnfjfndlqgrvhwnnnccvgdfqtwlmbwcpdtgscfpvmbdtcdmmgqrfjvnhngqsdtzhlbjwrrcrjfswwrgbhznlwhcjlsfprbqqcqmbdjhgjmmtqmjpldgqvptqcwjmlrjtjwdfbbvhpsnmfvdwnrntqzhfgfmrtgwgddpqvvdjqvrdwdwrsbjlbrmrjjbbpjpqgsjdzfjcrsnbmtmrstcrztzhgswgghwbfltdsvrcqvvjtmjwznnnwtsmshvbbpzwltrjpmbgsbqwphmwlhgpltsgjmgbdfrlhcbfjnvpvdwzccgdhswtgplcqnsjdwfbhbbpssvfrjbzmcphzjdncjgsvrcrplhqpnwdgfvrjqgfshdwrqjdvjmggtnnghqrccgddnzndcgpgpghtvrpwftfpttvgwqqcjbvnmqzlshdrdj";
  let lines: Vec<&str> = contents.trim().split("\n").collect();
  let first_line: &str = lines[0];
  alert("Retrieved input...");

  let start_of_packet_idx = find_start_of_packet_index(first_line);
  let start_of_msg_idx = find_start_of_message_index(first_line);

  println!("Start of packet index: {}", start_of_packet_idx);
  let msg = format!("Start of packet index: {}\nStart of message index: {}", start_of_packet_idx, start_of_msg_idx);
  alert(msg.as_str());
}

#[cfg(test)]
mod tests {
  use super::*;
  #[test]
  fn it_finds_start_of_packet() {
    let start_idx = find_start_of_packet_index("abbccdefg12345");
    assert_eq!(start_idx, 8);
    let start_idx = find_start_of_packet_index("bvwbjplbgvbhsrlpgdmjqwftvncz");
    assert_eq!(start_idx, 5);
    let start_idx = find_start_of_packet_index("nppdvjthqldpwncqszvftbrmjlhg");
    assert_eq!(start_idx, 6);
  }
  #[test]
  fn it_finds_start_of_message() {
    let start_idx = find_start_of_message_index("mjqjpqmgbljsphdztnvjfqwrcgsmlb");
    assert_eq!(start_idx, 19);
    let start_idx = find_start_of_message_index("bvwbjplbgvbhsrlpgdmjqwftvncz");
    assert_eq!(start_idx, 23);
    let start_idx = find_start_of_message_index("nppdvjthqldpwncqszvftbrmjlhg");
    assert_eq!(start_idx, 23);
  }
}
