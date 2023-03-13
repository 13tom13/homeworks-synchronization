import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static final String LETTERS = "RLRFR";
    public static final int LENGHT = 100;

    public static final int THREAD_COUNT = 1000;

    private static int maxRepeat = 0;
    private static int maxRepeatKey;

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                String route = generateRoute(LETTERS, LENGHT);
                int repeatR = 0;
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') repeatR++;
                }
                synchronized (sizeToFreq) {
                    if (!sizeToFreq.containsKey(repeatR)) {
                        sizeToFreq.put(repeatR, 1);
                    } else {
                        sizeToFreq.put(repeatR, sizeToFreq.get(repeatR) + 1);
                        if (maxRepeat <= sizeToFreq.get(repeatR)) {
                            maxRepeat = sizeToFreq.get(repeatR);
                            maxRepeatKey = repeatR;
                        }
                    }
                }
            });
            thread.start();
            thread.join();
        }
        System.out.format("Самое частое количество повторений %d (встретилось %d раз)\n", maxRepeatKey, maxRepeat);
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> repetition : sizeToFreq.entrySet()) {
            System.out.format("- %d (%d раз)\n", repetition.getKey(), repetition.getValue());
        }
    }
}
