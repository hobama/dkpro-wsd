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

package de.tudarmstadt.ukp.dkpro.wsd.wsdannotators;

import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.descriptor.ExternalResource;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.wsd.algorithms.WSDAlgorithmDocumentBasic;
import de.tudarmstadt.ukp.dkpro.wsd.si.SenseInventoryException;
import de.tudarmstadt.ukp.dkpro.wsd.type.WSDItem;

/**
 * An annotator which calls a {@link WSDAlgorithmDocumentBasic} disambiguation
 * algorithm once for each {@link WSDItem} in the document.
 *
 * @author Nicolai Erbs <erbs@ukp.informatik.tu-darmstadt.de>
 *
 */
public class WSDAnnotatorDocumentBasic
extends WSDAnnotatorBaseDocument
{

	public final static String WSD_ALGORITHM_RESOURCE = "WSDAlgorithmResource";
	@ExternalResource(key = WSD_ALGORITHM_RESOURCE)
	protected WSDAlgorithmDocumentBasic wsdMethod;

	@Override
	public void initialize(UimaContext context)
	throws ResourceInitializationException
	{
		super.initialize(context);
		inventory = wsdMethod.getSenseInventory();
	}

	protected Map<String, Double> getDisambiguation(JCas aJCas)
	throws SenseInventoryException
	{
		return wsdMethod.getDisambiguation(DocumentMetaData.get(aJCas).getDocumentId());
	}


	@Override
	protected String getDisambiguationMethod()
	throws SenseInventoryException
	{
		if (disambiguationMethodName != null) {
			return disambiguationMethodName;
		}
		else {
			return wsdMethod.getDisambiguationMethod();
		}
	}
}