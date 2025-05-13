package pl.edu.pja.tpo4blog.services;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo4blog.entities.Table;

@Service
public class PromptPrinter {
    private Table currentTable = Table.ARTICLES;
    private Mode currentMode = Mode.NONE;

    public PromptPrinter() {}

    public void print(){
        System.out.printf("\n%s%s > ", getModeString(), currentTable.name());
    }

    private String getModeString(){
        if(currentMode == Mode.NONE){
            return "";
        }
        return currentMode.toString() + " | ";
    }


    public Table getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(Table currentTable) {
        this.currentTable = currentTable;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }
}
