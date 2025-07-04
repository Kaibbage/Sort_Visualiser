package org.sorter;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sorter.ParseUtils.getListFromString;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:8081")  // Adjust the URL to your frontend's URL if necessary
public class SortController {

    private SortWebSocketHandler webSocketHandler;
    private SortAlgorithms sortAlgorithms;

    // Constructor injection of WebSocket handler
    public SortController(SortWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
        sortAlgorithms = new SortAlgorithms(webSocketHandler);
    }

    public static class InputRequest {
        private String input;

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }
    }

    @PostMapping("/solve-bubble")
    public String startSolvingBubbleSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.bubbleSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();


        return "";
    }

    @PostMapping("/solve-insertion")
    public String startSolvingInsertionSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.insertionSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();


        return "";
    }

    @PostMapping("/solve-selection")
    public String startSolvingSelectionSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.selectionSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }


    @PostMapping("/solve-merge")
    public String startSolvingMergeSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.mergeSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }

    @PostMapping("/solve-quick")
    public String startSolvingQuickSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.quickSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }

    @PostMapping("/solve-double-pivot-quick")
    public String startSolvingDoublePivotQuickSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.doublePivotQuickSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }

    @PostMapping("/solve-heap")
    public String startSolvingHeapSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.heapSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }

    @PostMapping("/solve-tim")
    public String startSolvingTimSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.timSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }

    @PostMapping("/solve-bogo")
    public String startSolvingBogoSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.bogoSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }

    @PostMapping("/solve-bozo")
    public String startSolvingBozoSort(@RequestBody InputRequest request){
        String input = request.getInput();

        String[] twoPart = input.split("::");

        int widgetNum = Integer.parseInt(twoPart[0]);
        String valsAsString = twoPart[1];
        List<Integer> vals = getListFromString(valsAsString);

        new Thread(() -> {
            try {
                sortAlgorithms.bozoSort(widgetNum, vals);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return "";
    }



//    @PostMapping("/start-solving-dijkstra")
//    public String startSolvingDijkstra(@RequestBody InputRequest request) {
//        String input = request.getInput();
//
//        int[][] grid = ParseUtils.getGridFromString(input);
//
//        new Thread(() -> {
//            try {
//                graphAlgorithms.dijkstra(grid);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//
//        return "Shortest Path solving started";
//    }






}
