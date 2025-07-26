bats_require_minimum_version 1.5.0

setup() {
    load 'test_helper/bats-support/load'
    load 'test_helper/bats-assert/load'

    # get the containing directory of this file
    # use $BATS_TEST_FILENAME instead of ${BASH_SOURCE[0]} or $0,
    # as those will point to the bats executable's location or the preprocessed file respectively
    DIR="$( cd "$( dirname "$BATS_TEST_FILENAME" )" >/dev/null 2>&1 && pwd )"
    # make executables in src/ visible to PATH
    PATH="$DIR/../nervat:$DIR/..:$PATH"
}

@test "tknzr_empty_line" {
    run -0 nervat inputs/tknzr/empty_line.n out
    assert_output ""
}

@test "tknzr_multi_empty_lines" {
    run -0 nervat inputs/tknzr/multi_empty_lines.n out
    assert_output ""
}

@test "tknzr_1str {
    run -0 nervat inputs/tknzr/1str.n out
    assert_output "test"
}

@test "tknzr_2i" {
    run -0 nervat inputs/tknzr/2i.n out
    assert_line "213"
    assert_line "3434"
}

@test "tknzr_line_over_80chars" {
    run -0 nervat inputs/tknzr/line_over_80chars.n out
}

@test "tknzr_printf_s" {
    run -0 nervat inputs/tknzr/printf_s.n out
    assert_line "printf"
    assert_line "\"test test test\""
}

@test "tknzr_printf_1i_param" {
    run -0 nervat inputs/tknzr/printf_1i_param.n out
    assert_line "\"test test %i test\""
    assert_line "123"
}

@test "tknzr_printf_1s_param" {
    run -0 nervat inputs/tknzr/printf_1s_param.n out
    assert_line "\"test test %s test\""
    assert_line "\"tes\""
}

@test "tknzr_multi_line_ok_s" {
    run -0 nervat inputs/tknzr/multi_line_ok_s.n out
    assert_line "test1"
    assert_line "test2"
    assert_line "test3"
    assert_line "root1"
    assert_line "root2"
    assert_line "root3"
}

@test "tknzr_multi_line_ok_i" {
    run -0 nervat inputs/tknzr/multi_line_ok_i.n out
    assert_line "1"
    assert_line "2"
    assert_line "3"
    assert_line "4"
    assert_line "5"
    assert_line "6"
}

@test "tknzr_multi_spaces_multi_lines" {
    run -0 nervat inputs/tknzr/multi_spaces_multi_lines.n out
    assert_line "t1"
    assert_line "t2"
    assert_line "t3"
    assert_line "t4"
    assert_line "z1"
    assert_line "z2"
    assert_line "z3"
    assert_line "z4"
}

@test "tknzr_multi_spaces_1line" {
    run -0 nervat inputs/tknzr/multi_spaces_1line.n out
    assert_line "one"
    assert_line "two"
    assert_line "three"
    assert_line "four"
}

@test "tknzr_just_1s_with_ws" {
    run -0 nervat inputs/tknzr/just_1s_with_ws.n out
    assert_line "\"dsfds fdsf fdfd\""
}

@test "tknzr_multi_line_1s_with_ws" {
    run -0 nervat inputs/tknzr/multi_line_1s_with_ws.n out
    assert_line "\"t1 t2 t3\""
    assert_line "\"z1 z2 z3\""
}

@test "tknzr_1emptystring" {
    run -0 nervat inputs/tknzr/1emptystring.n out
    assert_line "\"\""
}

@test "tknzr_2lines_emptystrings_withws" {
    run -0 nervat inputs/tknzr/2lines_emptystrings_withws.n out
    assert_line "\"\""
    assert_line "\"\""
    assert_line "\"\""
    assert_line "\"\""
}

@test "tknzr_2_lines_with_strings" {
    run -0 nervat inputs/tknzr/2_lines_with_strings.n out
}