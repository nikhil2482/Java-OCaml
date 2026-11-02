(******************************************************************************)
(* PROBLEM 5: WRITING TEST CASES                                              *)
(******************************************************************************)

(* The module `SetTest` defined below is a reuseable component that we'll use
   to test other modules that conform to the `SET` interface. When `SetTest`
   is instantiated with a particular set implementation, it will run all of
   its test cases against the set type defined in that implementation.  This
   means that the _same_ tests can be used for both the OrderedListSet and
   BSTSet implementations -- this makes sense because all implementations of
   `SET` should behave the same!

   Read through the module, then write your test cases in the space provided
   below. Make sure NOT to test for structural equality with sets.  Instead,
   use the equals function specified in the interface.  Your TAs will be
   grading the completeness of your tests. *)

module SetTest (SetImpl: SetInterface.SET) = struct
  ;; open SetImpl

  (* We first redefine the `run_test` and `run_failing_test` functions so that
     they prepend the name of the set we're testing to the test description. *)

  let run_test desc = Assert.run_test (debug_name ^ ": " ^ desc)
  let run_failing_test desc = Assert.run_failing_test (debug_name ^ ": " ^ desc)

  ;; print_endline ("\n--- Running tests for " ^ debug_name ^ " ---")

  (* Here are a couple of test cases to help get you started... *)

  let test () : bool =
    is_empty empty
  ;; run_test "is_empty: call on empty returns true" test

  (* Note that some tests in this test module (such as the one below) may not
     pass until all the functions they depend on are implemented. For
     instance, the test below will fail for sets whose `set_of_list` function
     is not yet implemented (even if `is_empty` is correct).  This is fine:
     the goal here is just to record all the tests that we expect will pass
     when we get around to implementing everything later. *)

  let test () : bool =
    let s = set_of_list [1; 2; 3] in
    not (is_empty s)
  ;; run_test "is_empty: non-empty set returns false" test


(* Now, it's your turn! Make sure to comprehensively test all the other
   functions defined in the `SET` interface. It will probably be helpful to
   have the file `setInterface.ml` open as you work.  Your tests should stress
   the abstract properties of what it means to be a set, as well as the
   relationships among the operations provided by the SET interface.

   One thing to be careful of: your tests should not use `=` to compare sets:
   use the `equals` function instead.

   We strongly advise you to write tests for the functions in the order they
   appear in the interface. Write tests for all of the functions here before
   you start implementing. After the tests are written, you should be able to
   implement the functions one at a time in the same order and see your tests
   incrementally pass.

   Your TAs will be manually grading the completeness of your test cases. *)

  (* ---------- Write your own test cases below. ---------- *)

let test () : bool =
  let s = set_of_list [] in
  list_of_set s = []
;; run_test "list_of_set: empty" test

let test () : bool =
  let s = set_of_list [0] in
  list_of_set s = [0]
;; run_test "list_of_set: singleton" test

let test () : bool =
  let s = set_of_list [0;1;2;0] in
  equals (s) (set_of_list [0;1;2])
;; run_test "list_of_set: duplicates case" test

let test () : bool =
  let s = set_of_list [0;1;2;0] in
  equals (s) (set_of_list [0;1;2])
;; run_test "list_of_set: duplicates case and accending order" test

let test () : bool =
  let s = set_of_list [0;1;2;] in
  equals (add 3 s) (set_of_list [0;1;2;3])
;; run_test "add: 4 integers together" test

let test () : bool =
  let s = set_of_list [0;1;2] in
  equals (add 2 s) (set_of_list [0;1;2;2])
;; run_test "add: number that is already in list" test

let test () : bool =
  let s = set_of_list ["CIS"; "1200"] in
  equals (add "HW" s) (set_of_list ["CIS"; "1200"; "HW"])
;; run_test "add: strings together" test

let test () : bool =
  let s = set_of_list ["CIS"; "1200"] in
  equals (add "CIS" s) (set_of_list ["CIS"; "1200"])
;; run_test "add: strings that contain duplicates in the beginning" test

let test () : bool =
  let s = set_of_list [0;1;2;3] in
  equals (remove 3 s) (set_of_list [0;1;2])
;; run_test "remove: number already contained in list" test

let test () : bool =
  let s = set_of_list [0;1;2;3] in
  equals (remove 4 s) (set_of_list [0;1;2;3])
;; run_test "remove: number not contained in list" test

let test () : bool =
  let s = set_of_list [0] in
  equals (remove 0 s) (set_of_list [])
;; run_test "remove: number from singleton" test

let test () : bool =
  let s = set_of_list [] in
  equals (remove 3 s) (set_of_list [])
;; run_test "remove: number not in an empty set" test

let test () : bool =
  let s = set_of_list ["CIS";"1200";"HW"] in
  equals (remove "CIS" s) (set_of_list ["1200";"HW"])
;; run_test "remove: string already contained in list in beginning of list" test

let test () : bool =
  let s = set_of_list ["CIS";"1200";"HW"] in
  equals (remove "3" s) (set_of_list ["CIS";"1200";"HW"])
;; run_test "remove: string not contained in list" test


let test () : bool =
  let s = set_of_list [0;1;2;3] in
  (member 3 s)
;; run_test "member: number already contained in list" test

let test () : bool =
  let s = set_of_list [0;1;2;3] in
  not (member 4 s)
;; run_test "member: number not already contained in list" test

let test () : bool =
  let s = set_of_list [0] in
  (member 0 s)
;; run_test "member: number already contained in list singleton" test

let test () : bool =
  let s = set_of_list [] in
  not (member 0 s)
;; run_test "member: number not contained in list empty" test

let test () : bool =
  let s = set_of_list [0;1;2;3] in
  (member 3 s)
;; run_test "member: number already contained in list" test

let test () : bool =
  let s = set_of_list ["CIS";"1200"] in
  (member "CIS" s)
;; run_test "member: string already contained in list" test

let test () : bool =
  let s = set_of_list ["CIS";"1200"] in
  not (member "HW" s)
;; run_test "member: string not contained in list" test

let test () : bool =
  let s = set_of_list [0;1;2;3] in
  size s = 4
;; run_test "size: 4 integers" test

let test () : bool =
  let s = set_of_list [] in
  size s = 0
;; run_test "size: empty list" test

let test () : bool =
  let s = set_of_list [0] in
  size s = 1
;; run_test "size: singleton" test

let test () : bool =
  let s = set_of_list [0;1;2;3;4;5] in
  size s = 6
;; run_test "size: large amount of integers" test

let test () : bool =
  let s = set_of_list ["CIS"; "1200"; "CIS"] in
  size s = 2
;; run_test "size: repeated strings" test

let test () : bool =
  let x = set_of_list [1] in
  let y = set_of_list [1] in
  equals x y
;; run_test "equals: singleton with same integer" test

let test () : bool =
  let x = set_of_list [] in
  let y = set_of_list [] in
  equals x y
;; run_test "equals: empty list is true" test

let test () : bool =
  let x = set_of_list [0] in
  let y = set_of_list [1] in
  not (equals x y)
;; run_test "equals: singleton with an empty list" test

let test () : bool =
  let x = set_of_list [1;2] in
  let y = set_of_list [1;2] in
  equals x y
;; run_test "equals: lists with more than one commonality" test

let test () : bool =
  let x = set_of_list [1;2] in
  let y = set_of_list [3;4] in
  not (equals x y)
;; run_test "equals: list with completely different elements" test


let test () : bool =
  let x = set_of_list ["CIS";"1200"] in
  let y = set_of_list ["CIS";"1200"] in
  equals x y
;; run_test "equals: string list with same elements" test

let test () : bool =
  let x = set_of_list ["CIS";"1200"; "HW"] in
  let y = set_of_list ["CIS";"1200"] in
  not (equals x y)
;; run_test "equals: string list with same elements but one is longer" test

let test () : bool =
  equals (set_of_list [0]) (set_of_list[0])
;; run_test "set_of_list: singleton integer" test

let test () : bool =
  equals (set_of_list []) (empty)
;; run_test "set_of_list: empty" test

let test () : bool =
  equals (set_of_list [0;1;1;2]) (set_of_list [0;1;2])
;; run_test "set_of_list: repeating integer" test

let test () : bool =
  equals (set_of_list ["CIS"; "1200"]) (set_of_list ["CIS"; "1200"])
;; run_test "set_of_list: empty" test

  (* ---------- Write your own test cases above. ---------- *)

end

(* The rest of the file instantiates the above tests so they are
   executed for both OrderedListSet and BSTSet.  Don't modify anything
   below this comment. *)

module TestOrderedListSet = SetTest(ListSet.OrderedListSet)
;; print_newline ()

module TestBSTSet = SetTest(TreeSet.BSTSet)
;; print_newline ()
