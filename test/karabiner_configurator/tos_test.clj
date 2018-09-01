(ns karabiner-configurator.tos-test
  (:require [karabiner-configurator.tos :as sut]
            [karabiner-configurator.data :refer :all]
            [clojure.test :as t]))

(init-conf-data)

(update-conf-data (assoc conf-data :modifiers {:1 {:mandatory ["left_command", "right_shift"]
                                                   :optional ["any"]}}))
(update-conf-data (assoc conf-data :input-source {:squirrel {:input_mode_id "com.googlecode.rimeime.inputmethod.Squirrel"
                                                             :input_source_id "com.googlecode.rimeime.inputmethod.Squirrel.Rime"
                                                             :language "zh-Hans"}
                                                  :us {:input_mode_id ""
                                                       :input_source_id "com.apple.keylayout.US"
                                                       :language "en"}}))

(def tos-example
  {:1 {:key :d}
   :2 {:key :d :modi :1}
   :3 {:key :d :modi :left_command}
   :4 {:key :d :modi [:left_command :right_shift]}
   :5 {:key :d :modi {:mandatory [:left_command :right_shift]}}
   :6 {:key :d :modi {:mandatory [:left_command :right_shift]
                      :optional [:caps_lock]}} ;; will ignore optional in tos
   :7 {:ckey :display_brightness_decrement}
   :8 {:ckey :display_brightness_decrement :modi :left_command}
   :9 {:pkey :button4 :modi :left_command}
   :10 {:shell "osascript -e 'display dialog \"Test dialog\"'"}
   :11 {:input :us}
   :12 {:set ["a" 1]}
   :13 {:set ["b" "layer b enabled"]}
   :14 {:mkey {:x -123 :y 123 :hwheel 13 :vwheel 1 :speed 2.0}}
   :15 {:key :d :modi :1 :lazy true :repeat false :halt true :hold_down_ms 30}
   :16 [{:key :d :modi {:mandatory [:left_command :right_shift]}
         :optional [:caps_lock]}
        {:key :d :modi :1 :lazy true :repeat false :halt true :hold_down_ms 30}
        {:input :us}
        {:ckey :display_brightness_decrement}]
   :17 {:key :!CT#OFa}})



(def result {:applications {},
             :tos {:14 [{:mouse_key {:x -123,
                                     :y 123,
                                     :horizontal_wheel 13,
                                     :vertical_wheel 1,
                                     :speed_multiplier 2.0}}],
                   :12 [{:set_variable {:name "a", :value 1}}],
                   :11 [{:select_input_source {:input_mode_id "",
                                               :input_source_id "com.apple.keylayout.US",
                                               :language "en"}}],
                   :10 [{:shell_command "osascript -e 'display dialog \"Test dialog\"'"}],
                   :13 [{:set_variable {:name "b",
                                        :value "layer b enabled"}}],
                   :4 [{:modifiers ["left_command" "right_shift"],
                        :key_code "d"}],
                   :16 [{:modifiers ["left_command" "right_shift"],
                         :key_code "d"}
                        {:modifiers ["left_command" "right_shift"],
                         :key_code "d",
                         :repeat false,
                         :halt true,
                         :hold_down_milliseconds 30,
                         :lazy true}
                        {:select_input_source {:input_mode_id "",
                                               :input_source_id "com.apple.keylayout.US",
                                               :language "en"}}
                        {:consumer_key_code "display_brightness_decrement"}],
                   :7 [{:consumer_key_code "display_brightness_decrement"}],
                   :1 [{:key_code "d"}],
                   :8 [{:modifiers ["left_command"],
                        :consumer_key_code "display_brightness_decrement"}],
                   :9 [{:modifiers ["left_command"],
                        :pointing_button "button4"}],
                   :17 [{:key_code "a",
                         :modifiers {:mandatory ["left_command" "left_control"]}}],
                   :2 [{:modifiers ["left_command" "right_shift"],
                        :key_code "d"}],
                   :5 [{:modifiers ["left_command" "right_shift"],
                        :key_code "d"}],
                   :15 [{:modifiers ["left_command" "right_shift"],
                         :key_code "d",
                         :repeat false,
                         :halt true,
                         :hold_down_milliseconds 30,
                         :lazy true}],
                   :3 [{:modifiers ["left_command"],
                        :key_code "d"}],
                   :6 [{:modifiers ["left_command" "right_shift"],
                        :key_code "d"}]},
             :swaps {},
             :input-source {:squirrel {:input_mode_id "com.googlecode.rimeime.inputmethod.Squirrel",
                                       :input_source_id "com.googlecode.rimeime.inputmethod.Squirrel.Rime",
                                       :language "zh-Hans"},
                            :us {:input_mode_id "",
                                 :input_source_id "com.apple.keylayout.US",
                                 :language "en"}},
             :modifiers {:1 {:mandatory ["left_command" "right_shift"],
                             :optional ["any"]}},
             :devices {},
             :layers {},
             :froms {},
             :raws {},
             :simlayers {}})

;; (sut/generate tos-example)

(t/deftest convert-tos
  (t/testing
   (t/is (= (sut/generate tos-example) result))))