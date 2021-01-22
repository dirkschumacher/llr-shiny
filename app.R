llr_interp <- llr::llr_env$new()
llr_interp$eval(paste0(readLines("app.clj"), collapse = "\n"))
