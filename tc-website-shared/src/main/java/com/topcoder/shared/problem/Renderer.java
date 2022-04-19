// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.language.Language;

public interface Renderer
{
    String toHTML(final Language p0) throws Exception;
    
    String toPlainText(final Language p0) throws Exception;
}
