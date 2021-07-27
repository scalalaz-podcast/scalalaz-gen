ThisBuild / shellPrompt := (state => s"[${Project.extract(state).currentRef.project}]> ")
