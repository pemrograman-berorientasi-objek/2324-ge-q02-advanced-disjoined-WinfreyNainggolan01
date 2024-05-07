package academic.model;

import java.util.ArrayList;

/**
 * @author 12S22001 Winfrey Nainggolan
 * @author 12S22012 Reinhard Batubara
 */
public class Generic {
    public static <E> void printList(ArrayList<E> elements) {
        elements.forEach(element -> System.out.println(element));
        
    }

    @SuppressWarnings("unlikely-arg-type")
    public static <E,T> boolean isExist(ArrayList<E> elements, T id) {
        for (E element : elements) {
            if (element.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
