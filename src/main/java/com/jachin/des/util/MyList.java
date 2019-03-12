package com.jachin.des.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Jachin
 * @since 2019/3/12 13:47
 */
public class MyList<T> extends ArrayList<T> {
    public MyList() {}

    public String toJson() {
        StringBuilder buf = new StringBuilder(100);
        this.toJson(buf);
        return buf.toString();
    }

    private void toJson(StringBuilder buf) {
        buf.append("[");
        Iterator<T> iter = this.iterator();
        boolean hasNext = iter.hasNext();

        while(hasNext) {
            T value = iter.next();
            JsonParse.toJson(value, buf);
            hasNext = iter.hasNext();
            if (hasNext) {
                buf.append(",");
            }
        }

        buf.append("]");
    }
    
    public String toString() {
        return this.toJson();
    }


}
