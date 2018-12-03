package ng.openbanking.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ng.openbanking.api.payload.customer.PocessingOperationResponse;
import ng.openbanking.api.payload.definition.OperationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Component
public class DataService {

    private final Logger log = LoggerFactory.getLogger(DataService.class);

    @Value("${OBN_HOME:}")
    private String OBN_HOME;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    private ObjectMapper objectMapper;

    public <T> T getSingleFromList(String dataName) {
        T data=null;
        List<T> dataList=getModelList(dataName);
        if(CollectionUtils.isEmpty(dataList)) {
            data=dataList.get(0);
        }
        return data;
    }

    public <T> List<T>  getModelList(String dataName) {
        return getData(dataName, List.class) ;
    }


    private File getOBNDataFile(String dataFileName) throws Exception {
        File file;
        String dataFilePath;
        if (StringUtils.isEmpty(OBN_HOME)) {
            dataFilePath=String.format("OBN_HOME/data/%s", dataFileName);
            log.debug("OBN_HOME not configured attempting to load from default {}",dataFilePath);
            ClassPathResource classPathResource = new ClassPathResource(dataFilePath);
            file =classPathResource.getFile();
        } else {
            dataFilePath=String.format("%s/data/%s",OBN_HOME, dataFileName);
            log.debug("attempting to load from default {}",dataFilePath);
            file=new File(dataFilePath);
        }
        if(file.exists()){
            log.debug("Reading data from actual path {}",file.getAbsolutePath());
        }
        return file;
    }

    public <T> T getData(String fileName, Class<T> type) {
        try {
            log.debug("Reading data from {} for {}", fileName, type.getSimpleName());
            File file=getOBNDataFile(fileName);
            if(file.exists()) {
                return this.objectMapper.readValue(file, type);
            }else{
                log.error("File not found for {} from {}", type, fileName);
            }
        } catch (Exception e) {
            log.warn("Cannot convert json data for {} using file {}", type, fileName);
            log.error(null, e);
        }
        return null;
    }

    public PocessingOperationResponse generateProcessingResponse(OperationStatus requiredStatus) {
        PocessingOperationResponse response=new PocessingOperationResponse();
//        response.setResponseCode(requiredStatus);
//        response.setTransactionReferenceId(System.currentTimeMillis()+"");
//        response.setMessage(requiredStatus.name());
        return response;
    }

    @PostConstruct
    protected void init() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
