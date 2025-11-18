package oreilly.foreign;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class SimpleScope {

    static void main() {
        var main = new SimpleScope();
        main.run();
    }

    public void run() {
        try (var offHeap = Arena.ofConfined()) {
//            MemorySegment cStr = offHeap.allocateFrom("Hello from Java 25!");
            MemorySegment cStr = offHeap.allocateFrom("常磐");

            String subStr = cStr.getString(1);
            System.out.println(subStr);
        }
    }


    void foo() {
        // 1. Get the native linker and lookup the C function ("strlen" as example)
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
        MemorySegment strlenAddr = stdlib.find("strlen").orElseThrow();

        // 2. Define the descriptor: strlen takes (const char*) and returns size_t (long)
        FunctionDescriptor strlenDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);

        // 3. Create method handle for the C function
        MethodHandle strlen = linker.downcallHandle(strlenAddr, strlenDescriptor);

        // 4. Allocate native memory for the Java string (encoded as UTF-8 C string)
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment cStr = offHeap.allocateFrom("Hello from Java 25!");
            // 5. Call the C function with the native string pointer
            long length = (long) strlen.invoke(cStr);
            System.out.println("Length of string: " + length);
        } catch (Throwable t) {

        }
    }
}
