package oreilly.mh;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;


public class MHExamples implements Comparable<MHExamples> {
    private int f = 0;

    public MHExamples() {

    }

    public MHExamples(int i) {
        this.f = i;
    }

    void main() {
        // toString()
        var mtToString = MethodType.methodType(String.class);

        IO.println(mtToString);
        var mh = getToStringMH(mtToString);
        IO.println(mh);

        try {
            var ret = mh.invoke(this);
            IO.println(ret);

            String rs = (String)mh.invokeExact(this);
            IO.println(rs);
        } catch (Throwable e) {
            e.printStackTrace();
        }

//        comparing();
//        refl();
    }

    public void comparing() {
        // A setter method
        var mtSetter = MethodType.methodType(void.class, Object.class);

        // compareTo() from Comparable<MHExamples>
        var mtCompare = MethodType.methodType(int.class, MHExamples.class);

        IO.println(mtSetter);
        IO.println(mtCompare);

        var o1 = new MHExamples(3);
        var o2 = new MHExamples(17);

        var lk = MethodHandles.lookup();

        try {
            var mh = lk.findVirtual(getClass(), "compareTo", mtCompare);
            var i = mh.invoke(o1, o2);
            IO.println(i.getClass());
            IO.println(i);
        } catch (NoSuchMethodException | IllegalAccessException mhx) {
            // Catches lookup problems
            throw new IllegalArgumentException(mhx);
        } catch (Throwable e) {
            // Catches execution problems
            throw new RuntimeException(e);
        }

    }

    public MethodHandle getToStringMH(MethodType mt) {
        var lk = MethodHandles.lookup();

        try {
            return lk.findVirtual(lk.lookupClass(), "toString", mt);
        } catch (NoSuchMethodException | IllegalAccessException mhx) {
            throw new IllegalArgumentException(mhx);
        }
    }

    public void refl() {
        try {
            Method mh = getClass().getMethod("toString");
            var ret = mh.invoke(this);
            IO.println(ret);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    @Override
    public int compareTo(MHExamples o) {
        return getF() > o.getF() ? 1 : getF() < o.getF() ? -1 : 0;
    }

    @Override
    public String toString() {
        return "MHExamples{" +
                "f=" + f +
                '}';
    }
}
