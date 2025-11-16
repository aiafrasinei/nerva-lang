# experiments

## BLOCK OR NAMESPACE

```json
{
    "name" : "",
    "blocktype" : "Block",
}

_b
;

empty_b
;

var_b
    test_i
    test_s = "fdf"
;
```


// You would need to add the Byte Buddy library to your project dependencies.

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

class Person {
    public String greet() {
        return "Hello!";
    }
}

public class ByteBuddyExample {
    public static void main(String[] args) throws Exception {
        // Create a new type by subclassing Person
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Person.class)
                // Add a new field named "age" of type int
                .defineField("age", int.class)
                // Add a getter for the new "age" field
                .defineMethod("getAge", int.class, net.bytebuddy.jar.asm.Opcodes.ACC_PUBLIC)
                .intercept(FixedValue.value(42)) // Always returns 42 for this example
                .make();

        // Load the new class into the JVM
        Class<?> dynamicPersonClass = dynamicType.load(ByteBuddyExample.class.getClassLoader())
                                                 .getLoaded();

        // Create an instance of the newly generated class
        Object dynamicPersonInstance = dynamicPersonClass.getDeclaredConstructor().newInstance();

        // Use reflection to call the new method
        java.lang.reflect.Method getAgeMethod = dynamicPersonClass.getMethod("getAge");
        int age = (int) getAgeMethod.invoke(dynamicPersonInstance);

        System.out.println("Dynamically added age: " + age); // Output: Dynamically added age: 42

        // You can also call original methods
        java.lang.reflect.Method greetMethod = dynamicPersonClass.getMethod("greet");
        String greeting = (String) greetMethod.invoke(dynamicPersonInstance);
        System.out.println(greeting); // Output: Hello!
    }
}

package org.nerva.model.containers;

import org.nerva.model.Var;

import java.util.HashMap;
import java.util.Map;

public class Vars {
    private final Map<String, Var> vars = new HashMap<>();

    public void set(String name, Var value) {
        vars.put(name, value);
    }

    public Var get(String name) {
        if (!vars.containsKey(name)) {
            throw new RuntimeException("Variable '" + name + "' not found.");
        }
        return vars.get(name);
    }

    public boolean has(String name) {
        return vars.containsKey(name);
    }
}