class Api::Priora::V2::BaseController < ApplicationController
  ApiError = Class.new(Exception) { attr_accessor :message, :code }

  rescue_from StandardError with: :internal_server_error
  rescue_from ApiError, with: :api_error

  PRIORA_PUBLIC_KEY = OpenSSL::PKey::RSA.new(File.read(Rails.application.credentials.priora[:public_key]))

  def params
    @params ||= JWT.decode(bearer_token, PRIORA_PUBLIC_KEY, true, {algorithm: 'RS256'}).first["data"]
  end

  def bearer_token
    request.headers["Authorization"]&.split("Bearer ")[-1]
  end

  def priora_ok
    {json: {data: {}}, status: 200}
  end

  def current_token
    return @token if @token
    @token = Token.find_by(token: request.headers["Access-Token"])
    raise ApiError.new("Access-Token not found") unless @token
    @token
  end

  def internal_server_eror(error)
    # TODO: implement logging and error handling system
    puts "InternalServerError: #{error.class} #{error.message}"

    render json: {
      error_class: "InternalServerError",
      error_message: "Something went wrong"
    }, status: 500
  end

  def api_error(error)
    render json: {
      error_class: error.class.demodulize,
      error_message: error.message
    }, status: error.code || 400
  end
end
