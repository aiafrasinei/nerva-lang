# FUNCTIONS

## function declaration

[name]_f = , intput_[type] , output_[type] ,
    ret output
;

```nerva
main_f = , argv_t , _i ,
    println "Hello world!"
    ret 0
;

sum_f = , a_i b_i , _i ,
    ret a + b
;
```

## function calls

```nerva
main argv_a
sum , 2 3 ,
```

point2d x_r y_r
points = , 2 3 , 4 5 ,
sum_f = , a_point2d , _point2d ,
    ret , a[0].x + a[0].y , a[1].x + a[1].x ,
;

sum points
