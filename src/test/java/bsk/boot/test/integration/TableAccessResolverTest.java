package bsk.boot.test.integration;

import bsk.boot.model.TableLabel;
import bsk.boot.service.TableAccessResolver;
import bsk.boot.service.MacTableAccessResolver;
import bsk.boot.repository.TableLabelRepository;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mateusz-PC on 06.01.2017.
 */
public class TableAccessResolverTest {

    private static String TABLE_LABEL_NAME = "etykiety";

    private static String TABLE_DIAGNOSIS_NAME = "diagnoza";

    private static String TABLE_DRUGS = "lek";

    @Test
    public void hasSaveAccessWhenLabelsSameAsUserLabelShouldBeTrue() {

        TableAccessResolver tableAccessResolver = prepareTableAccessResolver(3, 3, 3);
        boolean value = tableAccessResolver.hasSaveAccess(3, TABLE_LABEL_NAME, TABLE_DIAGNOSIS_NAME, TABLE_DRUGS);
        Assert.assertTrue(value);
    }

    @Test
    public void hasSaveAccessWhenLabelsLowerThanUserLabelShouldBeFalse() {
        TableAccessResolver tableAccessResolver = prepareTableAccessResolver(0, 0 ,0);
        boolean value = tableAccessResolver.hasSaveAccess(3, TABLE_LABEL_NAME, TABLE_DIAGNOSIS_NAME, TABLE_DRUGS);
        Assert.assertFalse(value);
    }

    @Test
    public void hasSaveAccessWhenTableLabelHigherThanUserLabelShouldBeFalse() {
        TableAccessResolver tableAccessResolver = prepareTableAccessResolver(4, 4 ,4);
        boolean value = tableAccessResolver.hasSaveAccess(3, TABLE_LABEL_NAME, TABLE_DIAGNOSIS_NAME, TABLE_DRUGS);
        Assert.assertFalse(value);
    }

    @Test
    public void hasSaveAccessWhenOtherLabelsHigherThanUserLabelShouldBeTrue() {
        TableAccessResolver tableAccessResolver = prepareTableAccessResolver(0, 4 ,4);
        boolean value = tableAccessResolver.hasSaveAccess(3, TABLE_LABEL_NAME, TABLE_DIAGNOSIS_NAME, TABLE_DRUGS);
        Assert.assertTrue(value);
    }

    private TableAccessResolver prepareTableAccessResolver(int tableLabel, int diagnosisLabel, int drugsLabel) {
        TableLabelRepository tableLabelRepositoryMock = mock(TableLabelRepository.class);
        when(tableLabelRepositoryMock.findOne(Matchers.eq(TABLE_LABEL_NAME)))
                .thenReturn(create(tableLabel, TABLE_LABEL_NAME));
        when(tableLabelRepositoryMock.findOne(Matchers.eq(TABLE_DIAGNOSIS_NAME)))
                .thenReturn(create(diagnosisLabel, ""));
        when(tableLabelRepositoryMock.findOne(Matchers.eq(TABLE_DRUGS)))
                .thenReturn(create(drugsLabel, ""));
        TableAccessResolver tableAccessResolver = new MacTableAccessResolver();

        ReflectionTestUtils.setField(tableAccessResolver, "tableLabelRepository", tableLabelRepositoryMock);
        ReflectionTestUtils.setField(tableAccessResolver, "labelTableName", TABLE_LABEL_NAME);
        return tableAccessResolver;
    }

    private TableLabel create(int label, String name){
        TableLabel tl = new TableLabel();
        tl.setLabel(label);
        tl.setName(name);
        return tl;
    }

}
