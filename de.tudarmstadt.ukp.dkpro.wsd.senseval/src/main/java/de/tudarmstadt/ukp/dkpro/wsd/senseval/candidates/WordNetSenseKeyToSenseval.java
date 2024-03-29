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

package de.tudarmstadt.ukp.dkpro.wsd.senseval.candidates;

import de.tudarmstadt.ukp.dkpro.wsd.candidates.SenseConverter;

/**
 * Converts all sense IDs in WordNet sense key format to Senseval sense
 * key format by replacing apostrophes with hyphens.  Note that this
 * has the potential to be a lossy conversion!
 * 
 * @author	Tristan Miller <miller@ukp.informatik.tu-darmstadt.de>
 */
public class WordNetSenseKeyToSenseval
	extends SenseConverter
{

	@Override
	public String convert(String senseId)
	{
		return senseId.replace('\'', '-');
	}

}
