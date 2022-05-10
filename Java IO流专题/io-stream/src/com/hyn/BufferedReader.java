package com.hyn;

public class BufferedReader extends Reader_{
    private Reader_ reader;

    public BufferedReader(Reader_ reader) {
        this.reader = reader;
    }

    public void readFiles(int num){
        for (int i =0;i<num;i++){
            reader.readeFile();
        }
    }

}
