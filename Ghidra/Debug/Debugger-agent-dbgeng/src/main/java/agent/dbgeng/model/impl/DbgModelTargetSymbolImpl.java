/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package agent.dbgeng.model.impl;

import java.util.List;
import java.util.Map;

import agent.dbgeng.manager.impl.DbgMinimalSymbol;
import agent.dbgeng.model.iface2.DbgModelTargetSymbol;
import ghidra.dbg.util.PathUtils;
import ghidra.program.model.address.Address;

public class DbgModelTargetSymbolImpl extends DbgModelTargetObjectImpl
		implements DbgModelTargetSymbol {
	protected static String indexSymbol(DbgMinimalSymbol symbol) {
		return symbol.getName();
	}

	protected static String keySymbol(DbgMinimalSymbol symbol) {
		return PathUtils.makeKey(indexSymbol(symbol));
	}

	protected final boolean constant;
	protected final Address value;
	protected final int size;

	public DbgModelTargetSymbolImpl(DbgModelTargetSymbolContainerImpl symbols,
			DbgMinimalSymbol symbol) {
		super(symbols.getModel(), symbols, keySymbol(symbol), "Symbol");
		this.constant = false;
		this.value = symbols.getModel().getAddressSpace("ram").getAddress(symbol.getAddress());
		this.size = 0;

		changeAttributes(List.of(), List.of(), Map.of( //
			// TODO: DATA_TYPE
			VALUE_ATTRIBUTE_NAME, value, //
			SIZE_ATTRIBUTE_NAME, size, //
			UPDATE_MODE_ATTRIBUTE_NAME, TargetUpdateMode.FIXED //
		), "Initialized");
	}

	@Override
	public boolean isConstant() {
		return constant;
	}

	@Override
	public Address getValue() {
		return value;
	}

	@Override
	public long getSize() {
		return size;
	}
}