package org.sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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
                delay(n);
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
            delay(n);
        }

        return minIndex;
    }

    public void mergeSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();
        mergeSortRecursive(widgetNum, vals, 0, n-1);
    }

    public void mergeSortRecursive(int widgetNum, List<Integer> vals, int l, int r) throws InterruptedException {
        if(l == r){
            return;
        }

        int m = (l+r)/2;

        mergeSortRecursive(widgetNum, vals, l, m); //left side
        mergeSortRecursive(widgetNum, vals, m+1, r); //right side

        merge(widgetNum, vals, l, m, m+1, r);
    }

    //note that the r here represents something different than the r in the parent function
    public void merge(int widgetNum, List<Integer> vals, int l, int lEnd, int r, int rEnd) throws InterruptedException {
        List<Integer> temp = new ArrayList<>();
        int savedL = l;

        //go until one runs out
        while(l <= lEnd && r <= rEnd){
            if(vals.get(l) < vals.get(r)){
                temp.add(vals.get(l));
                l++;
                delay(vals.size());
            }
            else{ //vals.get(r) <= vals.get(r)
                temp.add(vals.get(r));
                r++;
                delay(vals.size());
            }
        }

        //do the ones that have not run out, only one of these loops will ever be entered, not both
        while(l <= lEnd){
            temp.add(vals.get(l));
            l++;
            delay(vals.size());
        }
        while(r <= rEnd){
            temp.add(vals.get(r));
            r++;
            delay(vals.size());
        }


        for(int i = 0; i < temp.size(); i++){
            vals.set(savedL+i, temp.get(i));
            buildAndSendString(widgetNum, vals, vals.size());
        }
    }

    public void quickSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();

        quickSortRecursive(widgetNum, vals, 0, n-1);
    }

    public void quickSortRecursive(int widgetNum, List<Integer> vals, int l, int r) throws InterruptedException {
        if(l >= r){
            return;
        }

        List<Integer> left = new ArrayList<>();
        int middle = 0;
        List<Integer> right = new ArrayList<>();

        int pivot = vals.get(l);

        for(int i = l; i <= r; i++){
            int val = vals.get(i);
            if(val < pivot){
                left.add(val);
                delay(vals.size());
            }
            else if(val > pivot){
                right.add(val);
                delay(vals.size());
            }
            else{
                middle++;
                delay(vals.size());
            }
        }

        int index = l;
        for(int leftVal: left){
            vals.set(index, leftVal);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }
        for(int i = 0; i < middle; i++){
            vals.set(index, pivot);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }
        for(int rightVal: right){
            vals.set(index, rightVal);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }

        int leftEnd = l + left.size() - 1;
        int rightStart = leftEnd + 1 + middle;

        quickSortRecursive(widgetNum, vals, l, leftEnd);
        quickSortRecursive(widgetNum, vals, rightStart , r);

    }

    public void doublePivotQuickSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();
        doublePivotQuickSortRecursive(widgetNum, vals, 0, n-1);
    }

    public void doublePivotQuickSortRecursive(int widgetNum, List<Integer> vals, int l, int r) throws InterruptedException {
        if(l >= r) {
            return;
        }

        int pivot1 = vals.get(l);
        int pivot2 = vals.get(r);

        if(pivot1 > pivot2){
            int temp = pivot1;
            pivot1 = pivot2;
            pivot2 = temp;
        }

        List<Integer> left = new ArrayList<>();
        List<Integer> equalToPivot1 = new ArrayList<>();
        List<Integer> between = new ArrayList<>();
        List<Integer> equalToPivot2 = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for(int i = l; i <= r; i++){
            int val = vals.get(i);

            if(val < pivot1){
                left.add(val);
                delay(vals.size());
            }
            else if (val == pivot1){
                equalToPivot1.add(val);
                delay(vals.size());
            }
            else if (val > pivot1 && val < pivot2){
                between.add(val);
                delay(vals.size());
            }
            else if (val == pivot2){
                equalToPivot2.add(val);
                delay(vals.size());
            }
            else{
                right.add(val);
                delay(vals.size());
            }

        }

        int index = l;

        for(int v: left){
            vals.set(index, v);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }

        for(int v: equalToPivot1){
            vals.set(index, v);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }

        for(int v: between){
            vals.set(index, v);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }

        for(int v: equalToPivot2){
            vals.set(index, v);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }

        for(int v: right){
            vals.set(index, v);
            index++;
            buildAndSendString(widgetNum, vals, vals.size());
        }

        int leftEnd = l + left.size() - 1;
        int betweenStart = leftEnd + 1 + equalToPivot1.size();
        int betweenEnd = betweenStart + between.size() - 1;
        int rightStart = betweenEnd + 1 + equalToPivot2.size();

        doublePivotQuickSortRecursive(widgetNum, vals, l, leftEnd);
        doublePivotQuickSortRecursive(widgetNum, vals, betweenStart, betweenEnd);
        doublePivotQuickSortRecursive(widgetNum, vals, rightStart, r);

    }

    //building my own max heap for heapsort because i can't put delays on the operations using a priorityqueue T_T
    public void heapSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();

        for(int i = n/2 - 1; i >= 0; i--){
            heapify(widgetNum, vals, n, i);
        }


        for(int i = n-1; i > 0; i--){
            swap(vals, 0, i);
            buildAndSendString(widgetNum, vals, n);

            heapify(widgetNum, vals, i, 0);
        }
    }

    void heapify(int widgetNum, List<Integer> vals, int n, int i) throws InterruptedException {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if(l < n){
            delay(n);
            if(vals.get(l) > vals.get(largest)){
                largest = l;
            }
        }

        if(r < n){
            delay(n);
            if(vals.get(r) > vals.get(largest)){
                largest = r;
            }
        }

        if(largest != i){
            swap(vals, i, largest);
            buildAndSendString(widgetNum, vals, n);

            heapify(widgetNum, vals, n, largest);
        }
    }

    public void insertionSortInRange(int widgetNum, List<Integer> vals, int start, int end) throws InterruptedException {
        int n = vals.size();

        for(int i = start; i <= end; i++){
            int val = vals.get(i); //this will become vals.get(j+1) every time since it's switched in, could change it? maybe easier to understand

            for(int j = i-1; j >= start; j--){
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

    public void bogoSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();

        while(!isSorted(vals)){
            Collections.shuffle(vals);
            for(int i = 0; i < n-1; i++){
                delay(n);
            }
            buildAndSendString(widgetNum, vals, n);
        }
    }

    public void bozoSort(int widgetNum, List<Integer> vals) throws InterruptedException {
        int n = vals.size();

        while(!isSorted(vals)){
            int rand1 = (int) (Math.random() * n);
            int rand2 = (int) (Math.random() * n);
            swap(vals, rand1, rand2);
            delay(n);
            buildAndSendString(widgetNum, vals, n);
        }
    }

    //maybe add n delays in here if we want it to be realistic time like the others,
    //however that will also take forever as bogo and bozo sort are O(n!) as they check approximately every possibility
    //which for n numbers the number of orderings is n!
    public boolean isSorted(List<Integer> vals){
        for(int i = 0; i < vals.size()-1; i++){
            if(vals.get(i) > vals.get(i+1)){
                return false;
            }
        }

        return true;
    }



    public void delay(int n) throws InterruptedException {
        Thread.sleep(TIME/n);
    }


    //time slept should maybe be based on n
    //want to call this once for each operation (either compare or swap)
    //if the operation is smth i can't measure(removing from a heap) maybe add a logn delay option for a longer delay than per 1 operation
    public void buildAndSendString(int widgetNum, List<Integer> vals, int n) throws InterruptedException {
        delay(n);
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
