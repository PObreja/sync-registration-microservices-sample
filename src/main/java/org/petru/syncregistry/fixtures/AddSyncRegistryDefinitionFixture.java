package org.petru.syncregistry.fixtures;

import org.petru.syncregistry.services.ResponseMessage;
import org.petru.syncregistry.services.SyncRegistryDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fitnesse.fixtures.TableFixture;

/**
 * @author Lucian Tuca
 */
@Component public class AddSyncRegistryDefinitionFixture extends TableFixture
{

    public static final int FIRST_SYSTEM_ID_COLUMN = 0;
    public static final int FIRST_SYSTEM_ITEM_COLUMN = 1;
    public static final int SECOND_SYSTEM_ID_COLUMN = 2;
    public static final int SECOND_SYSTEM_ITEM_COLUMN = 3;
    public static final int STATUS_COLUMN = 4;
    public static final int EVALUATION_COLUMN = 5;

    private RestTemplate restTemplate = new RestTemplate();

    @Override public void doStaticTable(int rows)
    {
        for (int row = 1; row < rows; row++)
        {
            doRow(row);
        }
    }

    private void doRow(int row)
    {
        String firstSystemId = getText(row, FIRST_SYSTEM_ID_COLUMN);
        String firstSystemItem = getText(row, FIRST_SYSTEM_ITEM_COLUMN);
        String secondSystemId = getText(row, SECOND_SYSTEM_ID_COLUMN);
        String secondSystemItem = getText(row, SECOND_SYSTEM_ITEM_COLUMN);

        SyncRegistryDefinition syncRegistryDefinition = new SyncRegistryDefinition();
        syncRegistryDefinition.setFirstSystemId(firstSystemId);
        syncRegistryDefinition.setFirstSystemItem(firstSystemItem);
        syncRegistryDefinition.setSecondSystemId(secondSystemId);
        syncRegistryDefinition.setSecondSystemItem(secondSystemItem);

        processSyncRegistryDefinition(syncRegistryDefinition, row);
    }

    private void processSyncRegistryDefinition(SyncRegistryDefinition syncRegistryDefinition,
        int row)
    {
        String expectedStatus = getText(row, STATUS_COLUMN);
        String expectedMessage = getText(row, EVALUATION_COLUMN);

        ResponseEntity<ResponseMessage> responseEntity = restTemplate
            .postForEntity("http://localhost:1111/syncregistry", syncRegistryDefinition,
                ResponseMessage.class);
        ResponseMessage body = responseEntity.getBody();
        String statusCode = responseEntity.getStatusCode().toString();
        String response = body.getMessage();

        if (statusCode.equals(expectedStatus)) {
            if (response.equals(expectedMessage))
            {
                right(row, STATUS_COLUMN);
                right(row, EVALUATION_COLUMN);
            }
            else
            {
                wrong(row, EVALUATION_COLUMN, response);
            }
        } else {
            wrong(row, STATUS_COLUMN, "" + statusCode);
        }
    }
}
