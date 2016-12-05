package com.kalessil.phpStorm.phpInspectionsEA.inspectors.apiUsage;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpElementVisitor;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpInspection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RealpathOnRelativePathsInspector extends BasePhpInspection {
    private static final String message = "realpath() working differently in stream context (e.g. for phar://...), consider using dirname() instead";

    @NotNull
    public String getShortName() {
        return "RealpathOnRelativePathsInspection";
    }

    @Override
    @NotNull
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new BasePhpElementVisitor() {
            public void visitPhpFunctionCall(FunctionReference reference) {
                /* check general requirements */
                final String functionName = reference.getName();
                final PsiElement[] params = reference.getParameters();
                if (1 != params.length || StringUtil.isEmpty(functionName) || !functionName.equals("realpath")) {
                    return;
                }

                Collection<StringLiteralExpression> strings = PsiTreeUtil.findChildrenOfType(reference, StringLiteralExpression.class);
                if (strings.size() > 0) {
                    for (StringLiteralExpression oneString : strings) {
                        if (oneString.getContents().contains("..")) {
                            /* report the issue */
                            holder.registerProblem(reference, message, ProblemHighlightType.GENERIC_ERROR);
                            break;
                        }
                    }
                    strings.clear();
                }
            }
        };
    }
}