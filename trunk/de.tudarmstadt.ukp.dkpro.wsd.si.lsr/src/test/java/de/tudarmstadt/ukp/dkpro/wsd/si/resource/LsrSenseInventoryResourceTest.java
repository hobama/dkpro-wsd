/*******************************************************************************
 * Copyright 2013
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tudarmstadt.ukp.dkpro.wsd.si.resource;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitive;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.bindResource;

import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.descriptor.ExternalResource;

import de.tudarmstadt.ukp.dkpro.wsd.si.SenseInventory;
import de.tudarmstadt.ukp.dkpro.wsd.si.SenseInventoryException;

public class LsrSenseInventoryResourceTest
{

    public static class Annotator
        extends JCasAnnotator_ImplBase
    {
        final static String MODEL_KEY = "SenseInventory";
        @ExternalResource(key = MODEL_KEY)
        private SenseInventory inventory;

        @Override
        public void process(JCas aJCas)
            throws AnalysisEngineProcessException
        {
            int count = 0;
            try {
                System.out.println(inventory.getSenseInventoryName());
                for (Map.Entry<String, List<String>> entry : inventory
                        .getSenseInventory().entrySet()) {

                    // Looping over all of WordNet takes too long
                    if (count++ > 10) {
                        break;
                    }

                    System.out.println(entry.getKey() + " / "
                            + entry.getValue());
                }
            }
            catch (SenseInventoryException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    @Ignore
    @Test
    public void configureAggregatedExample()
        throws Exception
    {
        AnalysisEngineDescription desc = createPrimitiveDescription(Annotator.class);

        bindResource(desc, Annotator.MODEL_KEY,
                LsrSenseInventoryResource.class,
                LsrSenseInventoryResource.PARAM_RESOURCE_NAME, "wordnet",
                LsrSenseInventoryResource.PARAM_RESOURCE_LANGUAGE, "en");

        // Check the external resource was injected
        AnalysisEngine ae = createPrimitive(desc);
        ae.process(ae.newJCas());
    }
}