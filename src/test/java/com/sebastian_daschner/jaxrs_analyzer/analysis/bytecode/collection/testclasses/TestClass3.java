/*
 * Copyright (C) 2015 Sebastian Daschner, sebastian-daschner.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sebastian_daschner.jaxrs_analyzer.analysis.bytecode.collection.testclasses;

import com.sebastian_daschner.jaxrs_analyzer.model.Types;
import com.sebastian_daschner.jaxrs_analyzer.model.instructions.*;
import com.sebastian_daschner.jaxrs_analyzer.model.methods.MethodIdentifier;

import java.util.LinkedList;
import java.util.List;

public class TestClass3 {

    public int method(final int number) {
        try {
            return 3 * 2 / number;
        } catch (ArithmeticException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<Instruction> getResult() {
        final List<Instruction> instructions = new LinkedList<>();

        // constant folding
        instructions.add(new PushInstruction(6, Types.PRIMITIVE_INT));
        instructions.add(new LoadInstruction(1, Types.PRIMITIVE_INT, "number"));
        instructions.add(new SizeChangingInstruction("IDIV", 1, 2));
        instructions.add(new ReturnInstruction());

        instructions.add(new ExceptionHandlerInstruction());
        instructions.add(new StoreInstruction(2, Types.OBJECT));
        instructions.add(new LoadInstruction(2, "Ljava/lang/ArithmeticException;", "e"));
        instructions.add(new InvokeInstruction(MethodIdentifier.ofNonStatic("java/lang/ArithmeticException", "printStackTrace", Types.PRIMITIVE_VOID)));
        instructions.add(new PushInstruction(0, Types.PRIMITIVE_INT));
        instructions.add(new ReturnInstruction());

        return instructions;
    }

}
