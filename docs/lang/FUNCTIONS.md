# Functions

## function declaration

```nerva
[name]_f , intput_[type] ... , output_[type] ,
    [code]
    ret output
;
```

```nerva
main_f , argv_t , _i ,
    println "Hello world!"
    ret 0
;

sum_f , a_i b_i , _i ,
    ret a + b
;

extrapolate_f , , ,
    test_i
;
```

## function calls

```nerva
main argv
sum , 2 3 ,
extrapolate

point2d x_r y_r
points , 2 3 , 4 5 ,
sum_f , a_point2d , _point2d ,
    ret , a[0].x + a[0].y , a[1].x + a[1].x ,
;

sum points
```

## notes

* Function params have type annotations.
* Params are accessed without annotations inside the function.
