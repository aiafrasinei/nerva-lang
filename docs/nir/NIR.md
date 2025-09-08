# NERVA INTERMEDIATE REPRESENTATION (NIR)


## PROGRAM

```json
{
    "name" : "",
    "blocktype" : "Program",
    "data" : []
}
```

## VARIABLE DECLARATION, ASSIGNMENT , FUNCTION CALL PARAMETER

```json
{
    "name" : "name",
    "blocktype" : "Var",
    "datatype" : "",
    "data" : {}
}
```

## FUNCTION DECLARATION

```json
{
    "name" : "",
    "blocktype"  : "FunctDef",
    "inputs" : [],
    "outputs" : [],
    "code" : []
}
```

## FUNCTION CALL

```json
{
    "name" : "",
    "blocktype"  : "FunctCall",
    "inputs" : [],
    "outputs" : []
}
```

### NOTES

* for variables "data" : "" means default initialized variable