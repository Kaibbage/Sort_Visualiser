package org.sorter;

import java.util.List;

import static org.sorter.Constants.TIME;
import static org.sorter.ParseUtils.createSendBackString;

public class SortAlgorithms {
    private SortWebSocketHandler webSocketHandler;

    public SortAlgorithms(SortWebSocketHandler webSocketHandler){
        this.webSocketHandler = webSocketHandler;
    }

    public void bubbleSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();

        for(int end = n-1; end >= 0; end--){
            for(int i = 0; i <= end-1; i++){
                buildAndSendString(widgetNum, vals, n);
                if(vals.get(i) > vals.get(i+1)){
                    swap(vals, i, i+1);
                    buildAndSendString(widgetNum, vals, n);
                }
            }
        }
    }

    public void swap(List<Integer> vals, int index1, int index2){
        int temp = vals.get(index1);
        vals.set(index1, vals.get(index2));
        vals.set(index2, temp);
    }

    public void insertionSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();

        for(int i = 0; i < n; i++){
            int val = vals.get(i); //this will become vals.get(j+1) every time since it's switched in, could change it? maybe easier to understand

            for(int j = i-1; j >= 0; j--){
                if(val < vals.get(j)){
                    swap(vals, j, j+1);
                    buildAndSendString(widgetNum, vals, n);
                }
                else{
                    break;
                }
            }

        }
    }

    public void selectionSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();

        for(int i = 0; i < n; i++){
            int minIndex = getMinIndex(vals, i, n, widgetNum);
            swap(vals, minIndex, i);
            buildAndSendString(widgetNum, vals, n);
        }
    }

    public int getMinIndex(List<Integer> vals, int start, int n, int widgetNum) throws InterruptedException {
        int minIndex = start;

        for(int i = start+1; i < n; i++){
            if(vals.get(i) < vals.get(minIndex)){
                minIndex = i;
            }
            buildAndSendString(widgetNum, vals, n);
        }

        return minIndex;
    }


    //time slept should maybe be based on n
    //want to call this once for each operation (either compare or swap)
    //if the operation is smth i can't measure(removing from a heap) maybe add a logn delay option for a longer delay than per 1 operation
    public void buildAndSendString(int widgetNum, List<Integer> vals, int n) throws InterruptedException {
        Thread.sleep(TIME/n);
        String s = createSendBackString(widgetNum, vals);
        sendUpdateToFrontend(s);
    }


    // Method to send update to the frontend via WebSocket
    private void sendUpdateToFrontend(String value) {
        if (webSocketHandler != null) {
            webSocketHandler.sendUpdate(value);
        }
    }


}
