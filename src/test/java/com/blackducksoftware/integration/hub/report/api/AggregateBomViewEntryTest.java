package com.blackducksoftware.integration.hub.report.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.joda.time.DateTime;
import org.junit.Test;

import com.blackducksoftware.integration.hub.report.risk.api.RiskCategories;
import com.blackducksoftware.integration.hub.report.risk.api.RiskCounts;
import com.blackducksoftware.integration.hub.report.risk.api.RiskProfile;

public class AggregateBomViewEntryTest {

    @Test
    public void testAggregateBomViewEntry() {
        final RiskCounts counts1 = new RiskCounts(1, 1, 1, 1, 1);
        final RiskCounts counts2 = new RiskCounts(0, 1, 0, 0, 0);
        final RiskCounts counts3 = new RiskCounts(0, 0, 1, 0, 0);
        final RiskCounts counts4 = new RiskCounts(1, 0, 0, 0, 0);

        final RiskCategories categories1 = new RiskCategories(counts1, counts2, counts3, counts2, counts3);
        final RiskCategories categories2 = new RiskCategories(counts3, counts2, counts3, counts1, counts1);

        final int numberOfItems1 = 1;
        final int numberOfItems2 = 2;

        final RiskProfile riskProfile1 = new RiskProfile(numberOfItems1, categories1);
        final RiskProfile riskProfile2 = new RiskProfile(numberOfItems2, categories2);

        final List<String> bomEntryIds1 = new ArrayList<String>();
        final UUID uuid = UUID.randomUUID();
        bomEntryIds1.add(uuid.toString());
        final List<Long> bomViewEntryIds1 = new ArrayList<Long>();
        bomViewEntryIds1.add(15236l);
        final List<String> matchTypes1 = new ArrayList<String>();
        matchTypes1.add("string");
        final List<String> producerMatchTypes1 = new ArrayList<String>();
        producerMatchTypes1.add("string");
        final List<String> componentMatchTypes1 = new ArrayList<String>();
        componentMatchTypes1.add("string");
        final List<String> usages1 = new ArrayList<String>();
        usages1.add("string");
        final List<Boolean> inUses1 = new ArrayList<Boolean>();
        inUses1.add(true);
        final List<UserData> createdByUsers1 = new ArrayList<UserData>();
        createdByUsers1.add(new UserData("string", "string"));
        final String since1 = new DateTime().toString();

        final String id1 = "id1";
        final String name1 = "name1";
        final boolean restructured1 = true;
        final ProjectData producerProject1 = new ProjectData(id1, name1, restructured1);
        final List<ReleaseData> producerReleases1 = new ArrayList<ReleaseData>();
        producerReleases1.add(new ReleaseData("string", "string"));
        final List<LicenseDefinition> licenses1 = new ArrayList<LicenseDefinition>();
        licenses1.add(new LicenseDefinition("string", "string", "string", "string", "string", "string", "string"));

        final List<String> bomEntryIds2 = new ArrayList<String>();
        bomEntryIds2.add("");
        bomEntryIds2.add(null);
        bomEntryIds2.add("NotAUUID");
        final List<Long> bomViewEntryIds2 = new ArrayList<Long>();
        final List<String> matchTypes2 = new ArrayList<String>();
        final List<String> producerMatchTypes2 = new ArrayList<String>();
        final List<String> componentMatchTypes2 = new ArrayList<String>();
        final List<String> usages2 = new ArrayList<String>();
        final List<Boolean> inUses2 = new ArrayList<Boolean>();
        final List<UserData> createdByUsers2 = new ArrayList<UserData>();
        final String since2 = "since2";

        final ProjectData producerProject2 = new ProjectData(null, null, null);
        final List<ReleaseData> producerReleases2 = new ArrayList<ReleaseData>();
        final List<LicenseDefinition> licenses2 = new ArrayList<LicenseDefinition>();

        AggregateBomViewEntry item1 = new AggregateBomViewEntry(bomEntryIds1, bomViewEntryIds1, matchTypes1, producerMatchTypes1, componentMatchTypes1,
                usages1, inUses1, createdByUsers1, since1, producerProject1, producerReleases1, licenses1, riskProfile1);
        AggregateBomViewEntry item2 = new AggregateBomViewEntry(bomEntryIds2, bomViewEntryIds2, matchTypes2, producerMatchTypes2, componentMatchTypes2,
                usages2, inUses2, createdByUsers2, since2, producerProject2, producerReleases2, licenses2, riskProfile2);
        AggregateBomViewEntry item3 = new AggregateBomViewEntry(bomEntryIds1, bomViewEntryIds1, matchTypes1, producerMatchTypes1, componentMatchTypes1,
                usages1, inUses1, createdByUsers1, since1, producerProject1, producerReleases1, licenses1, riskProfile1);
        AggregateBomViewEntry item4 = new AggregateBomViewEntry(null, null, null, null, null,
                null, null, null, null, null, null, null, null);

        assertEquals(bomEntryIds1, item1.getBomEntryIds());
        assertEquals(uuid, item1.getBomEntryUUIds().get(0));
        assertEquals(bomViewEntryIds1, item1.getBomViewEntryIds());
        assertEquals(matchTypes1, item1.getMatchTypes());
        assertEquals(producerMatchTypes1, item1.getProducerMatchTypes());
        assertEquals(componentMatchTypes1, item1.getComponentMatchTypes());
        assertEquals(usages1, item1.getUsages());
        assertEquals(inUses1, item1.getInUses());
        assertEquals(createdByUsers1, item1.getCreatedByUsers());
        assertEquals(since1, item1.getSince());
        assertEquals(since1, item1.getSinceTime().toString());
        assertEquals(producerProject1, item1.getProducerProject());
        assertEquals(producerReleases1, item1.getProducerReleases());
        assertEquals(licenses1, item1.getLicenses());
        assertEquals("string", item1.getLicensesDisplay());
        assertEquals(riskProfile1, item1.getRiskProfile());

        assertEquals(counts1, item1.getVulnerabilityRisk());
        assertEquals(counts2, item1.getActivityRisk());
        assertEquals(counts3, item1.getVersionRisk());
        assertEquals(counts2, item1.getLicenseRisk());
        assertEquals(counts3, item1.getOperationalRisk());

        assertEquals("M", item1.getLicenseRiskString());
        assertEquals("L", item1.getOperationalRiskString());

        assertEquals("H", item2.getLicenseRiskString());
        assertEquals("H", item2.getOperationalRiskString());

        assertEquals(bomEntryIds2, item2.getBomEntryIds());
        assertTrue(item2.getBomEntryUUIds().isEmpty());
        assertEquals(bomViewEntryIds2, item2.getBomViewEntryIds());
        assertEquals(matchTypes2, item2.getMatchTypes());
        assertEquals(producerMatchTypes2, item2.getProducerMatchTypes());
        assertEquals(componentMatchTypes2, item2.getComponentMatchTypes());
        assertEquals(usages2, item2.getUsages());
        assertEquals(inUses2, item2.getInUses());
        assertEquals(createdByUsers2, item2.getCreatedByUsers());
        assertEquals(since2, item2.getSince());
        assertNull(item2.getSinceTime());
        assertEquals(producerProject2, item2.getProducerProject());
        assertEquals(producerReleases2, item2.getProducerReleases());
        assertEquals(licenses2, item2.getLicenses());
        assertEquals("", item2.getLicensesDisplay());
        assertEquals(riskProfile2, item2.getRiskProfile());

        assertEquals("", item4.getLicensesDisplay());

        assertTrue(!item1.equals(item2));
        assertTrue(item1.equals(item3));

        EqualsVerifier.forClass(AggregateBomViewEntry.class).suppress(Warning.STRICT_INHERITANCE).verify();

        assertTrue(item1.hashCode() != item2.hashCode());
        assertEquals(item1.hashCode(), item3.hashCode());

        StringBuilder builder = new StringBuilder();
        builder.append("AggregateBomViewEntry [bomEntryIds=");
        builder.append(item1.getBomEntryIds());
        builder.append(", bomViewEntryIds=");
        builder.append(item1.getBomViewEntryIds());
        builder.append(", matchTypes=");
        builder.append(item1.getMatchTypes());
        builder.append(", producerMatchTypes=");
        builder.append(item1.getProducerMatchTypes());
        builder.append(", componentMatchTypes=");
        builder.append(item1.getComponentMatchTypes());
        builder.append(", usages=");
        builder.append(item1.getUsages());
        builder.append(", inUses=");
        builder.append(item1.getInUses());
        builder.append(", createdByUsers=");
        builder.append(item1.getCreatedByUsers());
        builder.append(", since=");
        builder.append(item1.getSince());
        builder.append(", producerProject=");
        builder.append(item1.getProducerProject());
        builder.append(", producerReleases=");
        builder.append(item1.getProducerReleases());
        builder.append(", licenses=");
        builder.append(item1.getLicenses());
        builder.append(", riskProfile=");
        builder.append(item1.getRiskProfile());
        builder.append("]");
        assertEquals(builder.toString(), item1.toString());
    }
}
