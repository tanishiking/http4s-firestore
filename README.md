```sh
$ gcloud auth application-default login
$ scala-cli main.scala -- your_project_id $(gcloud auth application-default print-access-token)
```