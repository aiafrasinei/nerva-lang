bats_require_minimum_version 1.5.0

setup() {
    load 'test_helper/bats-support/load'
    load 'test_helper/bats-assert/load'

    # get the containing directory of this file
    # use $BATS_TEST_FILENAME instead of ${BASH_SOURCE[0]} or $0,
    # as those will point to the bats executable's location or the preprocessed file respectively
    DIR="$( cd "$( dirname "$BATS_TEST_FILENAME" )" >/dev/null 2>&1 && pwd )"
    # make executables in src/ visible to PATH
    PATH="$DIR/../n2nir/target/classes/:$DIR/..:$DIR/../tests:$PATH"
}

@test "DefaultInitVar" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/DefaultInitVar.n out
    jq empty out
}

@test "DefaultInitString" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/DefaultInitString.n out
    jq empty out
}

@test "DefaultInitBoolean" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/DefaultInitBoolean.n out
    jq empty out
}

@test "DefaultInitVars" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/DefaultInitVars.n out
    jq empty out
}

@test "VarAssign" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/VarAssign.n out
    jq empty out
}

@test "AssignmentEmptyString" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/AssignmentEmptyString.n out
    jq empty out
}

@test "AssignmentBoolean" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/AssignmentBoolean.n out
    jq empty out
}

@test "FunctCallHelloWorld" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/FunctCallHelloWorld.n out
    jq empty out
}

@test "FuncCallNoParams" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/FunctCallNoParams.n out
    jq empty out
}

@test "FunctCalls" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/FunctCalls.n out
    jq empty out
}

@test "FunctDeclNoParamsRet" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/FunctDeclNoParamsRet.n out
    jq empty out
}

@test "FunctDeclWithRet" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/FunctDeclWithRet.n out
    jq empty out
}

@test "FunctMain" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/FunctMain.n out
    jq empty out
}

@test "If" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/If.n out
    jq empty out
}

@test "IfElse" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/IfElse.n out
    jq empty out
}

@test "IfSimple" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/IfSimple.n out
    jq empty out
}

@test "ForSimple" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/ForSimple.n out
    jq empty out
}

@test "ForTable" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/ForTable.n out
    jq empty out
}

@test "ForWhile" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/ForWhile.n out
    jq empty out
}

@test "FizzBuzz" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/examples/fizzbuzz.n out
    jq empty out
}
