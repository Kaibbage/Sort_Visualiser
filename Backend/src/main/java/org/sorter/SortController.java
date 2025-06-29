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
