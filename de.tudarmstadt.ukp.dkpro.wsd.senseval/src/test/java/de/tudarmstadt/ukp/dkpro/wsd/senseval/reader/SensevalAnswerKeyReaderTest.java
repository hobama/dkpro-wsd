/*******************************************************************************
 * Copyright 2015
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

package de.tudarmstadt.ukp.dkpro.wsd.senseval.reader;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.JCasIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.wsd.senseval.reader.Senseval2LSReader;
import de.tudarmstadt.ukp.dkpro.wsd.senseval.reader.SensevalAnswerKeyReader;
import de.tudarmstadt.ukp.dkpro.wsd.senseval.writer.SensevalAnswerKeyWriter;
import de.tudarmstadt.ukp.dkpro.wsd.type.WSDItem;
import de.tudarmstadt.ukp.dkpro.wsd.type.WSDResult;

/**
 * @author	Tristan Miller <miller@ukp.informatik.tu-darmstadt.de>
 */
public class SensevalAnswerKeyReaderTest
{
	@Test
	public void sensevalAnswerKeyReaderTest()
    throws Exception
	{
		WSDItem w;
		WSDResult r;

		CollectionReader reader = createReader(
                Senseval2LSReader.class,
                Senseval2LSReader.PARAM_FILE, "classpath:/senseval/senseval2ls.xml"
                );
        AnalysisEngineDescription answerReader = createEngineDescription(
                SensevalAnswerKeyReader.class,
                SensevalAnswerKeyReader.PARAM_FILE, "classpath:/senseval/senseval2ls.key"
        );

        AnalysisEngine engine = createEngine(answerReader);
        JCasIterator i = new JCasIterator(reader, engine);
		assertTrue(i.hasNext());
		JCas j = i.next();

		w = JCasUtil.selectByIndex(j, WSDItem.class, 0);
		r = JCasUtil.selectByIndex(j, WSDResult.class, 0);
		System.out.println(SensevalAnswerKeyWriter.toSensevalAnswerKey(r));
		assertEquals(w, r.getWsdItem());
		assertEquals("this is a comment", r.getComment());
		assertEquals("call_for%2:32:03::", r.getSenses(0).getId());
		assertEquals(0.6, r.getSenses(0).getConfidence(), 0.001);
		assertEquals("call_for%2:42:00::", r.getSenses(1).getId());
		assertEquals(0.4, r.getSenses(1).getConfidence(), 0.001);

		r = JCasUtil.selectByIndex(j, WSDResult.class, -1);
		assertEquals(w, r.getWsdItem());

		assertTrue(i.hasNext());
		j = i.next();

		w = JCasUtil.selectByIndex(j, WSDItem.class, -1);
		r = JCasUtil.selectByIndex(j, WSDResult.class, -1);
		System.out.println(SensevalAnswerKeyWriter.toSensevalAnswerKey(r));
		assertEquals(w, r.getWsdItem());
		assertNull(r.getComment());
		assertEquals("animal%1:03:00::", r.getSenses(0).getId());
		assertEquals(0.0, r.getSenses(0).getConfidence(), 0.001);
		assertEquals("U", r.getSenses(1).getId());
		assertEquals(0.0, r.getSenses(1).getConfidence(), 0.001);
	}

}
