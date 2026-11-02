;; open Assert
;; open Dna
;; open Helices

(* The assertion library by default will run _all_ of the test cases associated
 * with this program.  However, while debugging you may prefer to have the
 * testing stop on the first failure that is encountered. Comment the line
 * below to turn off first-failure. *)
;; stop_on_failure ()


(******************************************************************************)
(********************** REMEMBER TO WRITE TESTS FIRST! ************************)
(********************* WRITE ALL TESTS BELOW THIS POINT ***********************)
(******************************************************************************)

(* If you'd like to test your helper functions, you should do that in dna.ml.
 * The only way you can test them in this file is by declaring them in
 * dna.mli - however if you do so your code won't compile with the grading
 * server because you don't submit your own dna.mli!
 *
 * If you'd like to write trees to use in your tests, put them in this file.
 * When writing trees of depth greater than 1 for tests, please indent all 
 * children by 4 spaces, separate children by line breaks, and place parentheses
 * similar to the following tree:
 *
 * LNode (
 *     LNode (
 *         LLeaf h1,
 *         h2,
 *         LLeaf h3
 *     ),
 *     h4,
 *     LLeaf h5
 * )
 *
*)

(************************ complementary_helix tests ***************************)

(* Here's an example test case.  Be sure to fill in the incomplete test cases
 * we provided. *)

let test () : bool =
  complementary_helix [T] = [A]
;; run_test "complementary_helix singleton" test

let test () : bool =
  complementary_helix [C; T; T; C] = [G; A; A; G]
;; run_test "complementary_helix multi-element list" test

let test () : bool =
  complementary_helix [C; T; A; A; T; G; T] = [G; A; T; T; A; C; A]
;; run_test "complementary_helix multi-element list with all nucleotides" test



(*************************** hamming_distance tests ***************************)

(* Here's an example test case.  Be sure to add your own--we will be grading the
 * tests for this problem. *)

let test () : bool =
  hamming_distance [G; A] [G; T] = 1
;; run_test "hamming_distance one different nucleotide" test

let test () : bool =
  hamming_distance [] [G; T] = 0
;; run_test "hamming_distance left empty nucleotide" test

let test () : bool =
  hamming_distance [G; A] [] = 0
;; run_test "hamming_distance right empty nucleotide" test

let test () : bool =
  hamming_distance [] [] = 0
;; run_test "hamming_distance two empty nucleotide" test

let test () : bool =
  hamming_distance [G; T; A] [C; T; T] = 2
;; run_test "hamming_distance two different nucleotide" test



(************************ decreasing_similarity tests *************************)

(* Be sure to add your own test cases -- we will be grading the tests for this 
 * problem. *)

(* Add your test here *)

let test () : bool =
  decreasing_similarity []
;; run_test "decreasing_similarity empty apes" test

let test () : bool =
  decreasing_similarity [chimpanzee]
;; run_test "decreasing_similarity one ape" test

let test () : bool =
  not(decreasing_similarity [lar_gibbon; white_cheeked_gibbon])
;; run_test "decreasing_similarity false apes" test

let test () : bool =
  decreasing_similarity (question_most_like_human())
;; run_test "decreasing_similarity humans like apes" test


(**************************** acids_of_helix tests ****************************)

(* Here's an example test case.  Be sure to add your own--we will be grading the
 * tests for this problem.  Also be sure to fill in the incomplete test case
 * we provided. *)

let test () : bool =
  acids_of_helix [A; G; T; A; C] = [Met]
;; run_test "acids_of_helix single codon" test

let test () : bool =
  acids_of_helix [A; G; T; A; C; T; A; C; T; C; C] = [Met; Met; Arg]
;; run_test "acids_of_helix multiple Mets at start" test

let test () : bool =
  acids_of_helix [] = []
;; run_test "acids_of_helix empty codon" test

let test () : bool =
  acids_of_helix [T; A; C; A; T; T] = [Met]
;; run_test "acids_of_helix single codon and end statement" test

let test () : bool =
  acids_of_helix [T; A; C; A; A; A; A; T; T] = [Met; Phe]
;; run_test "acids_of_helix double codon and end statement" test



(************ Kudos problem: all_acids_of_helix tests *************************)

(* These tests are commented out because they are for the kudos problem.
 * If you attempt this problem, remember to uncomment the tests. And write
 * some of your own! *)

(*
let test () : bool =
  all_acids_of_helix [T; A; C; A; C; T] = [[Met]]
;; run_test "all_acids_of_helix  [T; A; C; A; C; T]" test

let test () : bool =
  all_acids_of_helix [T; A; C; A; C; T; T; A; C; A; C; T] = [[Met]; [Met]]
;; run_test "all_acids_of_helix [T; A; C; A; C; T; T; A; C; A; C; T]" test

let test () : bool =
  all_acids_of_helix [T; A; C; A; C; T; G; A; T; A; C; A; C; T] = [[Met]; [Met]]
;; run_test "all_acids_of_helix [T; A; C; A; C; T; G; A; T; A; C; A; C; T]" test
*)


(***************************** count_leaves tests *****************************)

(* Here are two example tests cases. Both trees should contain exactly four
 * species. Don't forget to add your own test cases! *)

let test () : bool =
  count_leaves (greater_apes ()) = 4
;; run_test "count_leaves greater_apes" test

let test () : bool =
  count_leaves (lesser_apes ()) = 4
;; run_test "count_leaves lesser_apes" test

let test () : bool =
  not(count_leaves (lesser_apes ()) = 1)
;; run_test "count_leaves false case for lesser_apes" test

let test () : bool =
  not(count_leaves (greater_apes ()) = 1)
;; run_test "count_leaves false case for greater_apes" test

let test () : bool =
  count_leaves (Node (Leaf human, Node (Node (Leaf white_cheeked_gibbon, Leaf 
  lar_gibbon), Node (Leaf siamang, Leaf gorilla)))) = 5
;; run_test "count_leaves more than 4 leafs" test



(**************************** helix_of_tree tests *****************************)

(* Here's an example test case.  Be sure to add your own--we will be grading the
 * tests for this problem. *)

let test () : bool =
  helix_of_tree (LNode (LLeaf [T], [A], LLeaf [G])) = [A]
;; run_test "helix_of_tree lnode" test

let test () : bool =
  helix_of_tree (LLeaf [A]) = [A]
;; run_test "helix_of_tree with one lnode" test

let test () : bool =
  helix_of_tree (LNode (LLeaf [T], [], LLeaf [G])) = []
;; run_test "helix_of_tree with empty list lnode" test

let test () : bool =
  helix_of_tree (LNode (LLeaf [T; G], [A; T; G], LLeaf [G; T; C])) = [A; T; G]
;; run_test "helix_of_tree with unbalanced (more on right) lnode" test

let test () : bool =
  helix_of_tree (LNode (LLeaf [T; G; A], [T; G], LLeaf [G; T])) = [T; G]
;; run_test "helix_of_tree with unbalanced (more on left) lnode" test

(**************************** unlabel_tree tests ******************************)

(* Here's an example test case.  Be sure to add your own--we will be grading the
 * tests for this problem. *)

let test () : bool =
  unlabel_tree (LNode (LLeaf [T], [A], LLeaf [G])) =
  Node (Leaf [T], Leaf [G])
;; run_test "unlabel_tree depth-2 tree" test

let test () : bool =
  unlabel_tree (LLeaf [G]) = Leaf [G]
;; run_test "unlabel_tree one leaf tree" test

let test () : bool =
  unlabel_tree (LNode (LLeaf [T], [], LLeaf [G])) =
  Node (Leaf [T], Leaf [G])
;; run_test "unlabel_tree empty middle/depth-2 tree" test

let test () : bool =
  unlabel_tree (LNode (LNode(LLeaf [T], [A], LLeaf [G]), [C], LNode(LLeaf [G],
   [C], LLeaf[T]))) = Node (Node (Leaf[T], Leaf [G]), Node (Leaf[G], Leaf[T]))
;; run_test "unlabel_tree depth-3 tree" test


(************************* guess_parent_helix tests ***************************)

(* Here's an example test case.  Be sure to fill in the incomplete test case
 * we provided *)

let test () : bool =
  guess_parent_helix [T; C; A] [G; C; A] = [A; C; A]
;; run_test "guess_parent_helix one difference" test

let test () : bool =
  guess_parent_helix [A; C; G; G; T; A; C]
    [C; T; G; C; T; A; A] = [A; A; G; A; T; A; A]
;; run_test "guess_parent_helix multiple differences" test



(************************ add_ancestor_labels tests ***************************)

(* Here's an example test case.  Be sure to fill in the incomplete test cases
 * we provided and don't forget to add your own test cases! *)

let test () : bool =
  add_ancestor_labels (Node (Leaf [T], Leaf [G])) =
  LNode (LLeaf [T], [A], LLeaf [G])
;; run_test "add_ancestor_labels depth-2 tree" test

let test () : bool =
  add_ancestor_labels (Node (Leaf [T; C], Leaf [T; C])) =
  LNode (LLeaf [T; C], [T; C], LLeaf [T; C])
;; run_test "add_ancestor_labels also depth-2 tree" test

let test () : bool =
  add_ancestor_labels (Leaf [T; C]) =
  LLeaf [T; C]
;; run_test "add_ancestor_labels leaf" test

let test () : bool =
  add_ancestor_labels (Node (Node (Leaf[T], Leaf[G]), Node(Leaf[T], Leaf[C]))) 
  = LNode(LNode (LLeaf[T], [A], LLeaf[G]), [A], LNode(LLeaf[T], [A], LLeaf[C]))
;; run_test "add_ancestor_labels depth-3 tree" test 



(************************ parent_child_hamming tests **************************)

(* Here we give you one example test.  Add your own tests--we will grade the
 * test cases for this problem.  Be sure to test for trees of greater depth. *)

let test () : bool =
  parent_child_hamming (LNode (LLeaf [T], [A], LLeaf [G])) = 2
;; run_test "parent_child_hamming depth-2 tree, all different" test

let test () : bool =
  parent_child_hamming (LLeaf [A]) = 0
;; run_test "parent_child_hamming depth-2 tree, only one element" test

let test () : bool =
  parent_child_hamming (LNode (LLeaf [T], [A], LLeaf [A])) = 1
;; run_test "parent_child_hamming depth-2 tree, one different" test

let test () : bool =
  parent_child_hamming (LNode(LNode (LLeaf [T], [A], LLeaf [G]), [A], LLeaf[T]))
   = 3
;; run_test "parent_child_hamming depth-3 tree, all different" test




(**************************** simplest_tree tests *****************************)

(* Here are two example test cases. Don't forget to add your own test cases!
 *
 * Note: If you want to write a test that fails on purpose, then you can use
 * run_failing_test. run_failing_test will fail if the function returns true or 
 * false, and will pass if the function throws an error *)

let test () : bool =
  simplest_tree [] = (LLeaf [], 0)
;; run_failing_test "simplest_tree empty" test

let test () : bool =
  let t1 = LNode (LLeaf [A], [T], LLeaf [C]) in
  let t2 = LNode (
               LLeaf [G; T],
               [A; T],
               LNode (
                   LLeaf [T; T], 
                   [T; T], 
                   LLeaf [C; G]
               )
           ) in
  simplest_tree [t1; t2] = (t1, 2)
;; run_test "simplest_tree two tree list" test

let test () : bool =
  let t1 = LNode (LLeaf[A], [T], LLeaf[C]) in
  let t2 = LNode (LLeaf[G], [G], LLeaf[C]) in
  let t3 = LNode (LLeaf[G], [G], LLeaf[G]) in
  simplest_tree [t1; t2; t3] = (t3, 0)

;; run_test "simplest_tree three tree list" test
  



(********************* simplest_greater_ape_tree tests ************************)

(* Refactoring is the process of rewriting your code to make it simpler and
 * more general without changing its behavior. Therefore, it is easy to write
 * test cases for the new version---they should compute the same answers
 * that we did before. Because of this, we don't have to write any new test
 * cases! *)

let test () : bool =
  find_simplest_tree gorilla human chimpanzee orangutan =
  simplest_greater_ape_tree ()
;; run_test "simplest_greater_ape_tree" test

let test () : bool =
  find_simplest_tree lar_gibbon pileated_gibbon siamang white_cheeked_gibbon =
  simplest_lesser_ape_tree ()
;; run_test "simplest_lesser_ape_tree" test

;; print_endline "dnaTest.ml: ran to completion."
