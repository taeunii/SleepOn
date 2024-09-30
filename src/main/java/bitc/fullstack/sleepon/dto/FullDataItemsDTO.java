package bitc.fullstack.sleepon.dto;

import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
public class FullDataItemsDTO {
    private List<FullDataItemDTO> itemList;

    @XmlElement(name= "item")
    public List<FullDataItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<FullDataItemDTO> itemList) {
        this.itemList = itemList;
    }
}
