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

@test "HelloWorld" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/HelloWorld.n out
    jq empty out
}

@test "DefaultInitVar" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/DefaultInitVar.n out
    jq empty out
}

@test "DefaultInitVars" {
    run -0 java -cp $DIR/../n2nir/target/classes org.nerva.N2nir $DIR/inputs/DefaultInitVars.n out
    jq empty out
}