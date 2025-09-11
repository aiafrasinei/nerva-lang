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
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/DefaultInitVar.n out
    jq empty out
}

@test "DefaultInitString" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/DefaultInitString.n out
    jq empty out
}

@test "DefaultInitBoolean" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/DefaultInitBoolean.n out
    jq empty out
}

@test "DefaultInitVars" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/DefaultInitVars.n out
    jq empty out
}

@test "VarAssign" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/VarAssign.n out
    jq empty out
}

@test "AssignmentEmptyString" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/AssignmentEmptyString.n out
    jq empty out
}

@test "AssignmentBoolean" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/AssignmentBoolean.n out
    jq empty out
}

@test "FunctCallHelloWorld" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/FunctCallHelloWorld.n out
    jq empty out
}

@test "FuncCallNoParams" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/FunctCallNoParams.n out
    jq empty out
}

@test "FunctCalls" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/FunctCalls.n out
    jq empty out
}

@test "FunctDeclNoParamsRet" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/FunctDeclNoParamsRet.n out
    jq empty out
}

@test "FunctDeclWithRet" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/FunctDeclWithRet.n out
    jq empty out
}

@test "FunctMain" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/FunctMain.n out
    jq empty out
}

@test "If" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/If.n out
    jq empty out
}

@test "IfElse" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/IfElse.n out
    jq empty out
}

@test "IfSimple" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/IfSimple.n out
    jq empty out
}

@test "ForSimple" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/ForSimple.n out
    jq empty out
}

@test "ForTable" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/ForTable.n out
    jq empty out
}

@test "ForWhile" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/n2nir/ForWhile.n out
    jq empty out
}

@test "FizzBuzz" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/examples/FizzBuzz.n out
    jq empty out
}
