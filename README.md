#1. Task 3: LUCENE SEARCH ENGINE + EVALUATOR MODULE
Author: Victor S. Bursztyn (vbursztyn@cos.ufrj.br | vsb@poli.ufrj.br | vbursztyn@gmail.com).

#2. Requirements:

I. To have installed Eclipse IDE.

II. To download currently latest version of Lucene (5.1.0) from https://lucene.apache.org/, and to add "lucene-analyzers-common-5.1.0.jar", "lucene-core-5.1.0.jar" and "lucene-queryparser-5.1.0.jar" to Eclipse's imported project.

#3. Execution:

I. Running the project will generate "LCN_results_output.csv", which holds the results in the very same format used in Tasks 1 and 2.

II. You can go back to Task 2 and run "bash BATCH_RUN.sh" to generate all intermediate files. Then you can copy "LCN_results_output.csv" and rename it to "SR_results_output.csv", overwriting Task 2's original search results. Finally, run "bash BATCH_EVAL.sh" to get all evaluation metrics - this time, regarding Lucene's search.

#4. Results:

As previously, results may be found at folder "eval_diagrams":
"RELATORIO.txt" stores BATCH_EVAL.sh execution's output, which were graphically plotted and stored at "lucene_stemmer.pdf".